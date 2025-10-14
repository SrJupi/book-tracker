package com.srjupi.booktracker.backend.common.exceptions;

import java.net.URI;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public abstract class BookTracker500Exception extends BookTrackerBaseException {
    private final static URI TYPE = URI.create("/errors/internal");
    protected BookTracker500Exception(String title, String detail) {
        super(TYPE, title, INTERNAL_SERVER_ERROR, detail);
    }
}
