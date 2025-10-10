package com.srjupi.booktracker.backend.common.datafactory;

import com.srjupi.booktracker.backend.book.BookEntity;

import java.util.ArrayList;
import java.util.List;

public class BookTestDataFactory {

    //CONSTANTS FOR TEST DATA
    public static final String DEFAULT_TITLE = "The Pragmatic Programmer";
    public static final String DEFAULT_ENTITY_AUTHOR = "Andy Hunt,Dave Thomas";
    public static final List<String> DEFAULT_DTO_AUTHOR = List.of(DEFAULT_ENTITY_AUTHOR.split(","));
    public static final String DEFAULT_ISBN = "978013595705";
    public static final Integer DEFAULT_PAGES = 352;



    //METHODS TO CREATE TEST DATA
    public static BookEntity createValidBook() {
        BookEntity book = new BookEntity();
        book.setTitle(DEFAULT_TITLE);
        book.setAuthors(DEFAULT_ENTITY_AUTHOR);
        book.setPages(DEFAULT_PAGES);
        return book;
    }

    public static BookEntity createValidBookWithId() {
        BookEntity book = createValidBook();
        book.setId(1L);
        return book;
    }

    public static BookEntity createValidBookWithISBN() {
        BookEntity book = createValidBook();
        book.setIsbn(DEFAULT_ISBN);
        return book;
    }

    // Private constructor to prevent instantiation
    private BookTestDataFactory() {}
}
