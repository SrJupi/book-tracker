package com.srjupi.booktracker.backend.book.exceptions;

import com.srjupi.booktracker.backend.common.exceptions.BookTracker404Exception;

import static com.srjupi.booktracker.backend.book.BookConstants.BOOK_NOT_FOUND;
import static com.srjupi.booktracker.backend.book.BookConstants.DETAIL_NOT_FOUND_BY_ID;

public class Book404Exception extends BookTracker404Exception {

    private Book404Exception(String detail) {
        super(BOOK_NOT_FOUND, detail);
    }

    public static Book404Exception fromId(Long id) {
        return new Book404Exception(
                String.format(DETAIL_NOT_FOUND_BY_ID, id)
        );
    }


}
