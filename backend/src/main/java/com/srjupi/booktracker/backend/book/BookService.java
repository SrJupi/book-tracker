package com.srjupi.booktracker.backend.book;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BookEntity createBook(BookEntity entity) {
        // Basic check to avoid duplicate books based on ISBN
        // Need to add check for title and authors if ISBN is null
        if (entity.getIsbn() != null) {
            Optional<BookEntity> existingBook = repository.findByIsbn(entity.getIsbn());
            if (existingBook.isPresent()) {
                return existingBook.get();
            }
        }
        return repository.save(entity);
    }

    public void deleteBookById(Long id) {
        BookEntity book = getBookById(id);
        repository.delete(book);
    }

    public BookEntity getBookById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

    public List<BookEntity> getBooks() {
        return repository.findAll();
    }

    public BookEntity updateBook(Long id, BookEntity entity) {
        BookEntity existingBook = getBookById(id);
        existingBook.setTitle(entity.getTitle());
        existingBook.setAuthors(entity.getAuthors());
        existingBook.setIsbn(entity.getIsbn());
        return repository.save(existingBook);
    }

}
