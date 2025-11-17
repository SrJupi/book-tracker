package com.srjupi.booktracker.backend.book;

import com.srjupi.booktracker.backend.api.BooksApi;
import com.srjupi.booktracker.backend.api.dto.BookDto;
import com.srjupi.booktracker.backend.api.dto.BookPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class BookController implements BooksApi {

    private final BookService service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BookController (BookService bookService) {
        this.service = bookService;
    }

    @Override
    public ResponseEntity<BookDto> createBook(BookDto bookDto) {
        logger.info("POST /books called with body: {}", bookDto);
        BookDto createdBook = service.createBook(bookDto);
        URI location = URI.create(String.format("/books/%s", createdBook.getId()));
        logger.info("POST /books created book with id: {} at location: {}", createdBook.getId(), location);
        return ResponseEntity.created(location).body(createdBook);
    }

    @Override
    public ResponseEntity<Void> deleteBook(Long id) {
        logger.info("DELETE /books/{} called", id);
        service.deleteBookById(id);
        logger.info("DELETE /books/{} completed", id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<BookDto> getBookById(Long id) {
        logger.info("GET /books/{} called", id);
        BookDto bookDto = service.getBookDtoById(id);
        logger.info("GET /books/{} returning: {}", id, bookDto);
        return ResponseEntity.ok(bookDto);
    }

    @Override
    public ResponseEntity<List<BookDto>> getBooks() {
        logger.info("GET /books called");
        List<BookDto> books = service.getBooks();
        logger.info("GET /books returning {} books", books.size());
        return ResponseEntity.ok(books);
    }

    @Override
    public ResponseEntity<BookPage> searchBooks(String title, String authors, String isbn, String publisher, String language, Integer page, Integer size) {
        logger.info("GET /books/search called with parameters\n\ttitle: {}\n\tauthors: {}\n\tisbn: {}\n\tpublisher: {}" +
                "\n\tlanguage: {}\n\tpage: {}\n\tsize: {}",
                title, authors, isbn, publisher, language, page, size);
        BookPage books = service.searchBooks(title, authors, isbn, publisher, language, page, size);
        logger.info("GET /books/search returning {} books", books.getTotalElements());
        return ResponseEntity.ok(books);
    }

    @Override
    public ResponseEntity<BookDto> updateBookById(Long id, BookDto bookDto) {
        logger.info("PUT /books/{} called with body: {}", id, bookDto);
        BookDto updatedBook = service.updateBook(id, bookDto);
        logger.info("PUT /books/{} updated book to: {}", id, updatedBook);
        return ResponseEntity.ok(updatedBook);
    }
}
