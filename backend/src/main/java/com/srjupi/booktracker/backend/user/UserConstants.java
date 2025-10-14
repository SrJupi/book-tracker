package com.srjupi.booktracker.backend.user;

public final class UserConstants {

    //Exceptions Constants
    public static final String USER_NOT_FOUND = "User Not Found";
    public static final String DETAIL_NOT_FOUND_BY_ID = "User with id %d not found";
    public static final String DETAIL_NOT_FOUND_BY_USERNAME = "User with username %s not found";
    public static final String DETAIL_NOT_FOUND_BY_EMAIL = "User with email %s not found";

    public static final String USER_ALREADY_EXISTS = "User Already Exists";
    public static final String DETAIL_USERNAME_ALREADY_EXISTS = "User with username %s already exists";
    public static final String DETAIL_EMAIL_ALREADY_EXISTS = "User with email %s already exists";

    public static final String USER_INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String DETAIL_INTERNAL_SERVER_ERROR = "An unexpected error occurred. Please try again later.";

    //Private constructor to prevent instantiation
    private UserConstants() {}
}
