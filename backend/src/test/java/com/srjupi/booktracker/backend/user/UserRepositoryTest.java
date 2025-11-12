package com.srjupi.booktracker.backend.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static com.srjupi.booktracker.backend.common.datafactory.UserTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private UserEntity savedUser;

    @BeforeEach
    void setup() {
        savedUser = userRepository.save(createValidUser());
    }

    @Test
    void existsByUsername_ShouldReturnTrue_WhenUserExists() {
        boolean exists = userRepository.existsByUsername(DEFAULT_USERNAME);
        assertTrue(exists);
    }

    @Test
    void existsByUsername_ShouldReturnFalse_WhenUserDoesNotExist() {
        boolean exists = userRepository.existsByUsername("nonexistentuser");
        assertFalse(exists);
    }

    @Test
    public void existsByEmail_ShouldReturnTrue_WhenUserExists() {
        boolean exists = userRepository.existsByEmail(DEFAULT_EMAIL);
        assertTrue(exists);
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenUserDoesNotExist() {
        boolean exists = userRepository.existsByEmail("other@email.com");
        assertFalse(exists);
    }

    @Test
    void findByUsername_ShouldReturnUser_WhenUserExists() {
        Optional<UserEntity> user = userRepository.findByUsername(DEFAULT_USERNAME);
        assertThat(user).isPresent();
        assertThat(user.get().getUsername().equals(DEFAULT_USERNAME));
        assertThat(user.get().getEmail().equals(DEFAULT_EMAIL));
    }

    @Test
    void findByUsername_ShouldReturnEmpty_WhenUserDoesNotExists() {
        Optional<UserEntity> user = userRepository.findByUsername("nonexistentuser");
        assertThat(user).isNotPresent();
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenUserExists() {
        Optional<UserEntity> user = userRepository.findByEmail(DEFAULT_EMAIL);
        assertThat(user).isPresent();
        assertThat(user.get().getUsername().equals(DEFAULT_USERNAME));
        assertThat(user.get().getEmail().equals(DEFAULT_EMAIL));
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenUserDoesNotExists() {
        Optional<UserEntity> user = userRepository.findByEmail("other@email.com");
        assertThat(user).isNotPresent();
    }

    @Test
    void deleteByUsername_ShouldRemoveUser_WhenUserExists() {
        Optional<UserEntity> user = userRepository.findByUsername(DEFAULT_USERNAME);
        assertThat(user).isPresent();
        userRepository.deleteByUsername(DEFAULT_USERNAME);
        user = userRepository.findByUsername(DEFAULT_USERNAME);
        assertThat(user).isNotPresent();
    }

    @Test
    void deleteByEmail_ShouldRemoveUser_WhenUserExists() {
        Optional<UserEntity> user = userRepository.findByEmail(DEFAULT_EMAIL);
        assertThat(user).isPresent();
        userRepository.deleteByEmail(DEFAULT_EMAIL);
        user = userRepository.findByEmail(DEFAULT_EMAIL);
        assertThat(user).isNotPresent();
    }

    @Test
    void saveTwice_ShouldUpdateUser_WhenUserExists() {
        Optional<UserEntity> userOpt = userRepository.findByUsername(DEFAULT_USERNAME);
        assertThat(userOpt).isPresent();
        UserEntity user = userOpt.get();
        user.setEmail("newemail@email.com");
        UserEntity updateUser = userRepository.save(user);
        entityManager.flush();
        assertThat(updateUser.getUpdateAt()).isNotNull();
        assertThat(updateUser.getUpdateAt()).isAfter(updateUser.getCreatedAt());
    }
}
