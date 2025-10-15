package com.srjupi.booktracker.backend.common.exceptions;

import java.net.URI;

public abstract class BookTracker409Exception extends BookTrackerBaseException {
    private final static URI TYPE = URI.create("/errors/conflict");
    protected BookTracker409Exception(String title, String detail) {
        super(TYPE, title, detail);
    }
}
