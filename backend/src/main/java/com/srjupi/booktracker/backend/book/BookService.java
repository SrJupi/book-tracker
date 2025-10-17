package com.srjupi.booktracker.backend.book;

import com.srjupi.booktracker.backend.book.exceptions.Book404Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BookEntity createBook(BookEntity entity) {
        // Basic check to avoid duplicate books based on ISBN
        // Need to add check for title and authors if ISBN is null
        logger.info("createBook called");
        if (entity.getIsbn() != null) {
            Optional<BookEntity> existingBook = repository.findByIsbn(entity.getIsbn());
            if (existingBook.isPresent()) {
                logger.info("Book with ISBN: {} already exists with id: {}. Returning existing book.",
                        entity.getIsbn(), existingBook.get().getId());
                return existingBook.get();
            }
        }
        return repository.save(entity);
    }

    public void deleteBookById(Long id) {
        logger.info("deleteBookById called with id: {}", id);
        BookEntity book = getBookById(id);
        repository.delete(book);
    }

    public BookEntity getBookById(Long id) {
        logger.info("getBookById called with id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Book with id: {} not found. Throwing 404 Exception.", id);
                    return Book404Exception.fromId(id);
                });
    }

    public List<BookEntity> getBooks() {
        logger.info("getBooks called");
        return repository.findAll();
    }

    public BookEntity updateBook(Long id, BookEntity entity) {
        logger.info("updateBook called with id: {}", id);
        BookEntity existingBook = getBookById(id);
        existingBook.setTitle(entity.getTitle());
        existingBook.setAuthors(entity.getAuthors());
        existingBook.setIsbn(entity.getIsbn());
        return repository.save(existingBook);
    }

}
