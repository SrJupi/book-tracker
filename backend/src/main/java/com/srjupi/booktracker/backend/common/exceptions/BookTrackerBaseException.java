package com.srjupi.booktracker.backend.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.net.URI;

@Getter
public abstract class BookTrackerBaseException extends RuntimeException {
    private final URI type;
    private final String title;
    private final HttpStatus status;
    private final String detail;

    protected BookTrackerBaseException (URI type, String title, HttpStatus status, @Nullable String detail) {
        super(detail);
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
    }
}
