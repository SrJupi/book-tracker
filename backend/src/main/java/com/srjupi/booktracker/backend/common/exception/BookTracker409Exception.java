package com.srjupi.booktracker.backend.common.exception;

import java.net.URI;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

public abstract class BookTracker409Exception extends BookTrackerBaseException {
    private final static URI TYPE = URI.create("/errors/conflict");
    protected BookTracker409Exception(String title, String detail, URI instance) {
        super(TYPE, title, CONFLICT, detail, instance);
    }
}
