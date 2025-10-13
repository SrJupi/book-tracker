package com.srjupi.booktracker.backend.common.exception;

import com.srjupi.booktracker.backend.api.dto.ProblemDetail;
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
    private final URI instance;

    protected BookTrackerBaseException (URI type, String title, HttpStatus status, @Nullable String detail, @Nullable URI instance) {
        super(detail);
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
    }
}
