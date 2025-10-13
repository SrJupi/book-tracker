package com.srjupi.booktracker.backend.common.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public abstract class BookTracker400Exception extends BookTrackerBaseException {
    private final static URI TYPE = URI.create("/errors/bad-request");
    protected BookTracker400Exception(String title, String detail, URI instance) {
        super(TYPE, title, BAD_REQUEST, detail, instance);
    }
}
