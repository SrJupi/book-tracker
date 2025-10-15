package com.srjupi.booktracker.backend.common.exceptions;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.net.URI;

@Getter
public abstract class BookTrackerBaseException extends RuntimeException {
    private final URI type;
    private final String title;
    private final String detail;

    protected BookTrackerBaseException (@Nullable URI type, String title, @Nullable String detail) {
        super(detail);
        this.type = type;
        this.title = title;
        this.detail = detail;
    }
}
