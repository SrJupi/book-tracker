package com.srjupi.booktracker.backend.user.exceptions;

import com.srjupi.booktracker.backend.common.exceptions.BookTracker400Exception;

import static com.srjupi.booktracker.backend.user.UserConstants.*;

public class User400Exception extends BookTracker400Exception {

    private User400Exception(String detail) {
        super(USER_NOT_FOUND, detail);
    }


    public static User400Exception fromMissingUsername() {
        return new User400Exception(
                DETAIL_MISSING_USERNAME
        );
    }

    public static User400Exception fromMissingEmail() {
        return new User400Exception(
                DETAIL_MISSING_EMAIL
        );
    }
}
