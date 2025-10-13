package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.common.exception.BookTracker404Exception;

import java.net.URI;

import static com.srjupi.booktracker.backend.user.UserConstants.*;

public class User404Exception extends BookTracker404Exception {

    private User404Exception(String detail, String instance) {
        super(USER_NOT_FOUND, detail, URI.create(instance));
    }

    public static User404Exception fromId(Long id) {
        return new User404Exception(
                String.format(DETAIL_NOT_FOUND_BY_ID, id),
                String.format(INSTANCE_BY_ID, id)
        );
    }


}
