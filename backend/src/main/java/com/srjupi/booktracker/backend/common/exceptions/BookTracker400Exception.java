package com.srjupi.booktracker.backend.common.exceptions;

import java.net.URI;

public abstract class BookTracker400Exception extends BookTrackerBaseException {
    private final static URI TYPE = URI.create("/errors/bad-request");
    protected BookTracker400Exception(String title, String detail) {
        super(TYPE, title, detail);
    }
}
