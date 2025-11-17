package com.srjupi.booktracker.backend.book;

import com.srjupi.booktracker.backend.api.dto.BookDto;
import com.srjupi.booktracker.backend.api.dto.BookPage;
import com.srjupi.booktracker.backend.book.exceptions.Book404Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;
    private final BookMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BookService(BookRepository repository, BookMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public BookDto createBook(BookDto dto) {
        // Basic check to avoid duplicate books based on ISBN
        // Need to add check for title and authors if ISBN is null
        logger.info("createBook called");
        if (dto.getIsbn() != null) {
            Optional<BookEntity> existingBook = repository.findByIsbn(dto.getIsbn());
            if (existingBook.isPresent()) {
                logger.info("Book with ISBN: {} already exists with id: {}. Returning existing book.",
                        dto.getIsbn(), existingBook.get().getId());
                return mapper.toDto(existingBook.get());
            }
        }
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public void deleteBookById(Long id) {
        logger.info("deleteBookById called with id: {}", id);
        BookEntity book = getBookById(id);
        repository.delete(book);
    }

    public BookDto getBookDtoById(Long id) {
        logger.info("getBookByIdDto called with id: {}", id);
        BookEntity book = getBookById(id);
        return mapper.toDto(book);
    }

    private BookEntity getBookById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Book with id: {} not found. Throwing 404 Exception.", id);
                    return Book404Exception.fromId(id);
                });
    }

    public List<BookDto> getBooks() {
        logger.info("getBooks called");
        return mapper.toDto(repository.findAll());
    }

    public BookDto updateBook(Long id, BookDto entity) {
        logger.info("updateBook called with id: {}", id);
        BookEntity existingBook = getBookById(id);
        existingBook.setTitle(entity.getTitle());
        existingBook.setAuthors(String.join(",", entity.getAuthors()));
        existingBook.setIsbn(entity.getIsbn());
        return mapper.toDto(repository.save(existingBook));
    }

    public BookPage searchBooks(String title, String authors, String isbn,
                                String publisher, String language, Integer page,
                                Integer size) {
        logger.info("searchBooks called");
        Specification<BookEntity> specification = Specification.unrestricted();
        if (title != null && !title.isEmpty()) {
            specification = specification.and(BookSpecification.hasTitle(title));
        }
        if (authors != null && !authors.isEmpty()) {
            specification = specification.and(BookSpecification.hasAuthors(authors));
        }
        if (isbn != null && !isbn.isEmpty()) {
            specification = specification.and(BookSpecification.hasIsbn(isbn));
        }
        if (publisher != null && !publisher.isEmpty()) {
            specification = specification.and(BookSpecification.hasPublisher(publisher));
        }
        if (language != null && !language.isEmpty()) {
            specification = specification.and(BookSpecification.hasLanguage(language));
        }
        Page<BookEntity> resultPage = repository.findAll(specification, PageRequest.of(page, size));
        return mapper.toDto(resultPage);
    }
}
