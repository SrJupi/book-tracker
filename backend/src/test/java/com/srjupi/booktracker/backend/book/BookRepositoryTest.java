package com.srjupi.booktracker.backend.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static com.srjupi.booktracker.backend.common.datafactory.BookTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private BookEntity savedBook;

    @BeforeEach
    void setup() {
        savedBook = repository.save(createValidBookWithISBN());
    }

    @Test
    void findByIsbn_shouldReturnBook_whenBookExists() {
        Optional<BookEntity> foundBook = repository.findByIsbn(DEFAULT_ISBN);
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getIsbn().equals(DEFAULT_ISBN));
        assertThat(foundBook.get().getTitle().equals(savedBook.getTitle()));
        assertThat(foundBook.get().getAuthors().equals(savedBook.getAuthors()));
        assertEquals(foundBook.get().getPages(), savedBook.getPages());
    }

    @Test
    void findByIsbn_shouldReturnEmpty_whenBookExists() {
        Optional<BookEntity> foundBook = repository.findByIsbn("Not The ISBN You Are Looking For");
        assertThat(foundBook).isNotPresent();
    }


}
