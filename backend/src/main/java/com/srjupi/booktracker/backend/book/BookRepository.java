package com.srjupi.booktracker.backend.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByIsbn(String isbn);

}
