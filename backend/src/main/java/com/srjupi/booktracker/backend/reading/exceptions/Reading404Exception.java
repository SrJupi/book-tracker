package com.srjupi.booktracker.backend.reading.exceptions;

import com.srjupi.booktracker.backend.common.exceptions.BookTracker404Exception;

import static com.srjupi.booktracker.backend.reading.ReadingConstants.DETAIL_NOT_FOUND_BY_ID;
import static com.srjupi.booktracker.backend.reading.ReadingConstants.READING_NOT_FOUND;


public class Reading404Exception extends BookTracker404Exception {

    private Reading404Exception(String detail) {
        super(READING_NOT_FOUND, detail);
    }

    public static Reading404Exception fromId(Long id) {
        return new Reading404Exception(
                String.format(DETAIL_NOT_FOUND_BY_ID, id)
        );
    }
}
