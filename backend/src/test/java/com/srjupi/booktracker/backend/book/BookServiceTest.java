package com.srjupi.booktracker.backend.book;

import com.srjupi.booktracker.backend.api.dto.BookDTO;
import com.srjupi.booktracker.backend.api.dto.BookPage;
import com.srjupi.booktracker.backend.book.exceptions.Book404Exception;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import static com.srjupi.booktracker.backend.book.BookConstants.DETAIL_NOT_FOUND_BY_ID;
import static com.srjupi.booktracker.backend.common.datafactory.BookTestDataFactory.*;
import static com.srjupi.booktracker.backend.common.datafactory.BookTestDataFactory.createValidBook;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository repository;

    @Mock
    private Page<BookEntity> mockPage;

    @Mock
    private BookPage mockBookPage;

    @Mock
    private BookMapper mapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @Test
    void createBook_ShouldCreateBook_WhenBookIsValid() {
        BookDTO requestDTO = createValidBookDto();
        when(mapper.toEntity(any(BookDTO.class))).thenReturn(createValidBook());
        when(mapper.toDTO(any(BookEntity.class))).thenReturn(createValidBookDtoWithId());
        when(repository.save(any(BookEntity.class))).thenReturn(createValidBookWithId());
        BookDTO createdBook = service.createBook(requestDTO);
        assertNotNull(createdBook);
        assertEquals(1L, createdBook.getId());
        assertEquals(requestDTO.getTitle(), createdBook.getTitle());
        assertEquals(requestDTO.getAuthors(), createdBook.getAuthors());
        verify(repository, times(1)).save(any());
    }

    @Test
    void createBook_ShouldReturnExistingBook_WhenBookWithSameIsbnExists() {
        BookDTO requestDTO = createValidBookDtoWithISBN();
        when(repository.findByIsbn(anyString())).thenReturn(Optional.of(createValidBookWithId()));
        when(mapper.toDTO(any(BookEntity.class))).thenReturn(createValidBookDtoWithId());
        BookDTO createdBook = service.createBook(requestDTO);
        assertNotNull(createdBook);
        assertEquals(1L, createdBook.getId());
        assertEquals(requestDTO.getTitle(), createdBook.getTitle());
        assertEquals(requestDTO.getAuthors(), createdBook.getAuthors());
        verify(repository, never()).save(any());
    }

    @Test
    void createBook_ShouldCreateBook_WhenIsbnNotFoundInRepository() {
        BookDTO requestDTO = createValidBookDto();
        when(repository.save(any(BookEntity.class))).thenReturn(createValidBookWithId());
        when(mapper.toEntity(any(BookDTO.class))).thenReturn(createValidBook());
        when(mapper.toDTO(any(BookEntity.class))).thenReturn(createValidBookDtoWithId());
        BookDTO createdBook = service.createBook(requestDTO);
        assertNotNull(createdBook);
        assertEquals(1L, createdBook.getId());
        assertEquals(requestDTO.getTitle(), createdBook.getTitle());
        assertEquals(requestDTO.getAuthors(), createdBook.getAuthors());
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteBookById_ShouldDeleteBook_WhenBookExists() {
        BookEntity existingBook = createValidBookWithId();
        when(repository.findById(1L)).thenReturn(Optional.of(existingBook));
        doNothing().when(repository).delete(existingBook);
        service.deleteBookById(1L);
        verify(repository, times(1)).delete(existingBook);
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(createValidBookWithId()));
        when(mapper.toDTO(any(BookEntity.class))).thenReturn(createValidBookDtoWithId());
        BookDTO book = service.getBookDtoById(1L);
        assertNotNull(book);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getBookById_ShouldThrowException_WhenBookDoesNotExist() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(Book404Exception.class, () -> service.getBookDtoById(id));
        assertEquals(String.format(DETAIL_NOT_FOUND_BY_ID, id), exception.getMessage());
    }

    @Test
    void getBooks_shouldReturnListOfBooks() {
        service.getBooks();
        verify(repository, times(1)).findAll();
    }

    @Test
    void updateBook_ShouldUpdateBook_WhenBookExists() {
        BookDTO responseDTO = createValidBookDtoWithId();
        BookDTO updateDTO = createValidBookDto();
        responseDTO.setTitle("New Title");
        when(repository.findById(1L)).thenReturn(Optional.of(createValidBookWithId()));
        when(repository.save(any(BookEntity.class))).thenReturn(createValidBookWithId());
        when(mapper.toDTO(any(BookEntity.class))).thenReturn(responseDTO);
        BookDTO updatedBook = service.updateBook(1L, updateDTO);
        assertNotNull(updatedBook);
        assertEquals(responseDTO.getId(), updatedBook.getId());
        assertEquals("New Title", updatedBook.getTitle());
        assertEquals(responseDTO.getAuthors(), updatedBook.getAuthors());
        verify(repository, times(1)).save(any());
    }

    @Test
    void searchBooks_ShouldUseUnrestrictedSpecification_WhenAllFiltersAreNull() {
        when(repository.findAll(ArgumentMatchers.<Specification<BookEntity>>any(), any(Pageable.class)))
                .thenReturn(mockPage);
        when(mapper.toDTO(ArgumentMatchers.<Page<BookEntity>>any())).thenReturn(mockBookPage);

        BookPage result = service.searchBooks(
                null, null, null, null, null,
                DEFAULT_PAGE, DEFAULT_SIZE
        );

        assertThat(result).isEqualTo(mockBookPage);
        verify(repository).findAll(ArgumentMatchers.<Specification<BookEntity>>any(), eq(PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE)));
    }

    @Test
    void searchBooks_ShouldAddTitleSpecification_WhenTitleIsProvided() {
        String title = "Java";
        when(repository.findAll(ArgumentMatchers.<Specification<BookEntity>>any(), any(Pageable.class)))
                .thenReturn(mockPage);
        when(mapper.toDTO(ArgumentMatchers.<Page<BookEntity>>any())).thenReturn(mockBookPage);

        BookPage result = service.searchBooks(
                title, null, null, null, null,
                DEFAULT_PAGE, DEFAULT_SIZE
        );

        assertThat(result).isEqualTo(mockBookPage);
        verify(repository).findAll(ArgumentMatchers.<Specification<BookEntity>>any(), eq(PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE)));
    }

    @Test
    void searchBooks_ShouldCombineSpecifications_WhenMultipleFiltersProvided() {
        when(repository.findAll(ArgumentMatchers.<Specification<BookEntity>>any(), any(Pageable.class)))
                .thenReturn(mockPage);
        when(mapper.toDTO(ArgumentMatchers.<Page<BookEntity>>any())).thenReturn(mockBookPage);

        BookPage result = service.searchBooks(
                "Clean Code", "Robert Martin", "1234567890",
                "Prentice Hall", "EN", DEFAULT_PAGE, DEFAULT_SIZE
        );

        assertThat(result).isEqualTo(mockBookPage);
        verify(repository).findAll(ArgumentMatchers.<Specification<BookEntity>>any(), eq(PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE)));
    }

    @Test
    void searchBooks_ShouldIgnoreEmptyStrings() {
        when(repository.findAll(ArgumentMatchers.<Specification<BookEntity>>any(), any(Pageable.class)))
                .thenReturn(mockPage);

        service.searchBooks(
                "", "", "", "", "",
                DEFAULT_PAGE, DEFAULT_SIZE
        );

        verify(repository).findAll(ArgumentMatchers.<Specification<BookEntity>>any(), any(Pageable.class));
    }




}
