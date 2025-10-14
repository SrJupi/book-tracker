package com.srjupi.booktracker.backend.common.exceptions;

import java.net.URI;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public abstract class BookTracker404Exception extends BookTrackerBaseException {
    private final static URI TYPE = URI.create("/errors/not-found");
    protected BookTracker404Exception (String title, String detail) {
        super(TYPE, title, NOT_FOUND, detail);
    }
}
