package com.srjupi.booktracker.backend.user;

import org.h2.engine.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private UserEntity savedUser;

    @BeforeEach
    void setup() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("email@email.com");
        savedUser = userRepository.save(user);
    }

    @Test
    void existsByUsername_ShouldReturnTrue_WhenUserExists() {
        boolean exists = userRepository.existsByUsername("testuser");
        assertTrue(exists);
    }

    @Test
    void existsByUsername_ShouldReturnFalse_WhenUserDoesNotExist() {
        boolean exists = userRepository.existsByUsername("nonexistentuser");
        assertFalse(exists);
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenUserExists() {
        boolean exists = userRepository.existsByEmail("email@email.com");
        assertTrue(exists);
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenUserDoesNotExist() {
        boolean exists = userRepository.existsByEmail("other@email.com");
        assertFalse(exists);
    }

    @Test
    void findByUsername_ShouldReturnUser_WhenUserExists() {
        Optional<UserEntity> user = userRepository.findByUsername("testuser");
        assertThat(user).isPresent();
        assertThat(user.get().getUsername().equals("testuser"));
        assertThat(user.get().getEmail().equals("email@email.com"));
    }

    @Test
    void findByUsername_ShouldReturnEmpty_WhenUserDoesNotExists() {
        Optional<UserEntity> user = userRepository.findByUsername("nonexistentuser");
        assertThat(user).isNotPresent();
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenUserExists() {
        Optional<UserEntity> user = userRepository.findByEmail("email@email.com");
        assertThat(user).isPresent();
        assertThat(user.get().getUsername().equals("testuser"));
        assertThat(user.get().getEmail().equals("email@email.com"));
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenUserDoesNotExists() {
        Optional<UserEntity> user = userRepository.findByEmail("other@email.com");
        assertThat(user).isNotPresent();
    }

    @Test
    void deleteByUsername_ShouldRemoveUser_WhenUserExists() {
        Optional<UserEntity> user = userRepository.findByUsername("testuser");
        assertThat(user).isPresent();
        userRepository.deleteByUsername("testuser");
        user = userRepository.findByUsername("testuser");
        assertThat(user).isNotPresent();
    }

    @Test
    void deleteByEmail_ShouldRemoveUser_WhenUserExists() {
        Optional<UserEntity> user = userRepository.findByEmail("email@email.com");
        assertThat(user).isPresent();
        userRepository.deleteByEmail("email@email.com");
        user = userRepository.findByEmail("email@email.com");
        assertThat(user).isNotPresent();
    }
}
