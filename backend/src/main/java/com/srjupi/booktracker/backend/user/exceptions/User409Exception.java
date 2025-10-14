package com.srjupi.booktracker.backend.user.exceptions;

import com.srjupi.booktracker.backend.common.exceptions.BookTracker404Exception;
import com.srjupi.booktracker.backend.common.exceptions.BookTracker409Exception;

import java.net.URI;

import static com.srjupi.booktracker.backend.user.UserConstants.*;

public class User409Exception extends BookTracker409Exception {

    private User409Exception(String detail) {
        super(USER_ALREADY_EXISTS, detail);
    }

    public static User409Exception fromUsername(String username) {
        return new User409Exception(
                String.format(DETAIL_USERNAME_ALREADY_EXISTS, username)
        );
    }

    public static User409Exception fromEmail(String email) {
        return new User409Exception(
                String.format(DETAIL_EMAIL_ALREADY_EXISTS, email)
        );
    }


}
