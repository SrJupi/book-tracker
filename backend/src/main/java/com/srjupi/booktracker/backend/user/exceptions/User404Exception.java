package com.srjupi.booktracker.backend.user.exceptions;

import com.srjupi.booktracker.backend.common.exceptions.BookTracker404Exception;

import static com.srjupi.booktracker.backend.user.UserConstants.*;

public class User404Exception extends BookTracker404Exception {

    private User404Exception(String detail) {
        super(USER_NOT_FOUND, detail);
    }

    public static User404Exception fromId(Long id) {
        return new User404Exception(
                String.format(DETAIL_NOT_FOUND_BY_ID, id)
        );
    }

    public static User404Exception fromUsername(String username) {
        return new User404Exception(
                String.format(DETAIL_NOT_FOUND_BY_USERNAME, username)
        );
    }

    public static User404Exception fromEmail(String email) {
        return new User404Exception(
                String.format(DETAIL_NOT_FOUND_BY_EMAIL, email)
        );
    }
}
