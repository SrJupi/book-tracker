package com.srjupi.booktracker.backend.user.exceptions;

import com.srjupi.booktracker.backend.common.exceptions.BookTracker500Exception;

import static com.srjupi.booktracker.backend.user.UserConstants.*;

public class User500Exception extends BookTracker500Exception {

    public User500Exception() {
        super(USER_INTERNAL_SERVER_ERROR, DETAIL_INTERNAL_SERVER_ERROR);
    }

}
