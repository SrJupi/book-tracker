package com.srjupi.booktracker.backend.book;

import com.srjupi.booktracker.backend.book.exceptions.Book404Exception;
import com.srjupi.booktracker.backend.user.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static com.srjupi.booktracker.backend.common.datafactory.UserTestDataFactory.createValidUser;
import static com.srjupi.booktracker.backend.common.datafactory.UserTestDataFactory.createValidUserWithId;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository repository;

    @Mock
    private Page<BookEntity> mockPage;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @Test
    void createBook_ShouldCreateBook_WhenBookIsValid() {
        BookEntity book = createValidBook();
        when(repository.save(book)).thenReturn(createValidBookWithId());
        BookEntity createdBook = service.createBook(book);
        assertNotNull(createdBook);
        assertEquals(1L, createdBook.getId());
        assertEquals(book.getTitle(), createdBook.getTitle());
        assertEquals(book.getAuthors(), createdBook.getAuthors());
        verify(repository, times(1)).save(book);
    }

    @Test
    void createBook_ShouldReturnExistingBook_WhenBookWithSameIsbnExists() {
        BookEntity book = createValidBookWithISBN();
        when(repository.findByIsbn(book.getIsbn())).thenReturn(Optional.of(createValidBookWithId()));
        BookEntity createdBook = service.createBook(book);
        assertNotNull(createdBook);
        assertEquals(1L, createdBook.getId());
        assertEquals(book.getTitle(), createdBook.getTitle());
        assertEquals(book.getAuthors(), createdBook.getAuthors());
        verify(repository, never()).save(any());
    }

    @Test
    void createBook_ShouldCreateBook_WhenIsbnNotFoundInRepository() {
        BookEntity book = createValidBookWithISBN();
        when(repository.findByIsbn(book.getIsbn())).thenReturn(Optional.empty());
        when(repository.save(book)).thenReturn(createValidBookWithId());
        BookEntity createdBook = service.createBook(book);
        assertNotNull(createdBook);
        assertEquals(1L, createdBook.getId());
        assertEquals(book.getTitle(), createdBook.getTitle());
        assertEquals(book.getAuthors(), createdBook.getAuthors());
        verify(repository, times(1)).save(book);
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
        BookEntity existingBook = createValidBookWithId();
        when(repository.findById(1L)).thenReturn(Optional.of(existingBook));
        BookEntity book = service.getBookById(1L);
        assertNotNull(book);
        assertEquals(existingBook.getId(), book.getId());
        assertEquals(existingBook.getTitle(), book.getTitle());
        assertEquals(existingBook.getAuthors(), book.getAuthors());
    }

    @Test
    void getBookById_ShouldThrowException_WhenBookDoesNotExist() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(Book404Exception.class, () -> service.getBookById(id));
        assertEquals(String.format(DETAIL_NOT_FOUND_BY_ID, id), exception.getMessage());
    }

    @Test
    void getBooks_shouldReturnListOfBooks() {
        service.getBooks();
        verify(repository, times(1)).findAll();
    }

    @Test
    void updateBook_ShouldUpdateBook_WhenBookExists() {
        BookEntity existingBook = createValidBookWithId();
        BookEntity updatedData = createValidBook();
        updatedData.setTitle("New Title");
        when(repository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(repository.save(existingBook)).thenReturn(existingBook);
        BookEntity updatedBook = service.updateBook(1L, updatedData);
        assertNotNull(updatedBook);
        assertEquals(existingBook.getId(), updatedBook.getId());
        assertEquals("New Title", updatedBook.getTitle());
        assertEquals(existingBook.getAuthors(), updatedBook.getAuthors());
        verify(repository, times(1)).save(existingBook);
    }

    @Test
    void searchBooks_ShouldUseUnrestrictedSpecification_WhenAllFiltersAreNull() {
        when(repository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(mockPage);

        Page<BookEntity> result = service.searchBooks(
                null, null, null, null, null,
                DEFAULT_PAGE, DEFAULT_SIZE
        );

        assertThat(result).isEqualTo(mockPage);
        verify(repository).findAll(any(Specification.class), eq(PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE)));
    }

    @Test
    void searchBooks_ShouldAddTitleSpecification_WhenTitleIsProvided() {
        String title = "Java";
        when(repository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(mockPage);

        Page<BookEntity> result = service.searchBooks(
                title, null, null, null, null,
                DEFAULT_PAGE, DEFAULT_SIZE
        );

        assertThat(result).isEqualTo(mockPage);
        verify(repository).findAll(any(Specification.class), eq(PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE)));
    }

    @Test
    void searchBooks_ShouldCombineSpecifications_WhenMultipleFiltersProvided() {
        when(repository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(mockPage);

        Page<BookEntity> result = service.searchBooks(
                "Clean Code", "Robert Martin", "1234567890",
                "Prentice Hall", "EN", DEFAULT_PAGE, DEFAULT_SIZE
        );

        assertThat(result).isEqualTo(mockPage);
        verify(repository).findAll(any(Specification.class), eq(PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE)));
    }

    @Test
    void searchBooks_ShouldIgnoreEmptyStrings() {
        when(repository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(mockPage);

        service.searchBooks(
                "", "", "", "", "",
                DEFAULT_PAGE, DEFAULT_SIZE
        );

        verify(repository).findAll(any(Specification.class), any(Pageable.class));
    }




}
