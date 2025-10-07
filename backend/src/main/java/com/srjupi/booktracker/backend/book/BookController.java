package com.srjupi.booktracker.backend.book;

import com.srjupi.booktracker.backend.api.BooksApi;
import com.srjupi.booktracker.backend.api.dto.BookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class BookController implements BooksApi {

    private final BookService service;
    private final BookMapper mapper;

    public BookController (BookService bookService, BookMapper bookMapper) {
        this.service = bookService;
        this.mapper = bookMapper;
    }


    @Override
    public ResponseEntity<BookDTO> createBook(BookDTO bookDTO) {
        BookDTO createdBook = mapper.toDTO(service.createBook(mapper.toEntity(bookDTO)));
        URI location = URI.create(String.format("/books/%s", createdBook.getId()));
        return ResponseEntity.created(location).body(createdBook);
    }

    @Override
    public ResponseEntity<Void> deleteBook(Long id) {
        service.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<BookDTO> getBookById(Long id) {
        return ResponseEntity.ok(mapper.toDTO(service.getBookById(id)));
    }

    @Override
    public ResponseEntity<List<BookDTO>> getBooks() {
        return ResponseEntity.ok(mapper.toDTO(service.getBooks()));
    }

    @Override
    public ResponseEntity<BookDTO> updateBookById(Long id, BookDTO bookDTO) {
        return ResponseEntity.ok(mapper.toDTO(service.updateBook(id, mapper.toEntity(bookDTO))));
    }
}
