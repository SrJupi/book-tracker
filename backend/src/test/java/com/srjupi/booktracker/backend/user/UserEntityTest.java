package com.srjupi.booktracker.backend.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserEntityTest {

    @Test
    void userEntity_whenTested_shouldReturn100Coverage() {
        UserEntity user = new UserEntity();
        user.setUsername("lucas");
        user.setEmail("lucas@example.com");

        assertEquals("lucas", user.getUsername());
        assertEquals("lucas@example.com", user.getEmail());
    }
}