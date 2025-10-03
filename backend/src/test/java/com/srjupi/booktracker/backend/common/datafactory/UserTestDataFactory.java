package com.srjupi.booktracker.backend.common.datafactory;

import com.srjupi.booktracker.backend.api.dto.UserDTO;
import com.srjupi.booktracker.backend.user.UserEntity;

public class UserTestDataFactory {

    //CONSTANTS FOR TEST DATA
    public static final String DEFAULT_USERNAME = "testuser";
    public static final String DEFAULT_EMAIL = "email@email.com";



    //METHODS TO CREATE TEST DATA
    public static UserEntity createValidUser() {
        UserEntity user = new UserEntity();
        user.setEmail(DEFAULT_EMAIL);
        user.setUsername(DEFAULT_USERNAME);
        return user;
    }

    public static UserEntity createValidUserWithId() {
        UserEntity user = createValidUser();
        user.setId(1L);
        return user;
    }

    public static UserDTO createValidUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(DEFAULT_EMAIL);
        userDTO.setUsername(DEFAULT_USERNAME);
        return userDTO;
    }

    public static UserDTO createValidUserDTOWithId() {
        UserDTO userDTO = createValidUserDTO();
        userDTO.setId(1L);
        return userDTO;
    }



    // Private constructor to prevent instantiation
    private UserTestDataFactory() {}

}
