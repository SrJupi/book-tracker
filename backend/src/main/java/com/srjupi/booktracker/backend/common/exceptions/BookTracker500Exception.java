package com.srjupi.booktracker.backend.common.exceptions;

import java.net.URI;

public abstract class BookTracker500Exception extends BookTrackerBaseException {
    private final static URI TYPE = URI.create("/errors/internal");
    protected BookTracker500Exception(String title, String detail) {
        super(TYPE, title, detail);
    }
}
