package com.srjupi.booktracker.backend.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.srjupi.booktracker.backend.common.datafactory.UserTestDataFactory.createValidUser;
import static com.srjupi.booktracker.backend.common.datafactory.UserTestDataFactory.createValidUserWithId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void createUser_ShouldCreateUser_WhenUserIsValid() {
        UserEntity user = createValidUser();
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(createValidUserWithId());
        UserEntity createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals(1L, createdUser.getId());
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser_ShouldThrowException_WhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(createValidUser()));
        assertEquals("User with given email already exists", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_ShouldThrowException_WhenUserNameAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(createValidUser()));
        assertEquals("User with given username already exists", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        UserEntity existingUser = createValidUserWithId();
        UserEntity updatedData = createValidUser();
        updatedData.setUsername("newusername");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        UserEntity updatedUser = userService.updateUser(1L, updatedData);
        assertNotNull(updatedUser);
        assertEquals(existingUser.getId(), updatedUser.getId());
        assertEquals("newusername", updatedUser.getUsername());
        assertEquals(existingUser.getEmail(), updatedUser.getEmail());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.updateUser(1L, createValidUser()));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void getUsers_shouldReturnListOfUsers() {
        userService.getUsers();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        UserEntity existingUser = createValidUserWithId();
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        UserEntity user = userService.getUserById(1L);
        assertNotNull(user);
        assertEquals(existingUser.getId(), user.getId());
        assertEquals(existingUser.getUsername(), user.getUsername());
        assertEquals(existingUser.getEmail(), user.getEmail());
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(1L));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void getUserByUsername_ShouldReturnUser_WhenUserExists() {
        UserEntity existingUser = createValidUserWithId();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(existingUser));
        UserEntity user = userService.getUserByUsername(existingUser.getUsername());
        assertNotNull(user);
        assertEquals(existingUser.getId(), user.getId());
        assertEquals(existingUser.getUsername(), user.getUsername());
        assertEquals(existingUser.getEmail(), user.getEmail());
    }

    @Test
    void getUserByUsername_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserByUsername("nonexistentuser"));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void getUserByEmail_ShouldReturnUser_WhenUserExists() {
        UserEntity existingUser = createValidUserWithId();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));
        UserEntity user = userService.getUserByEmail(existingUser.getEmail());
        assertNotNull(user);
        assertEquals(existingUser.getId(), user.getId());
        assertEquals(existingUser.getUsername(), user.getUsername());
        assertEquals(existingUser.getEmail(), user.getEmail());
    }

    @Test
    void getUserByEmail_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserByEmail("abc@xyz.com"));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void deleteUserById_ShouldDeleteUser_WhenUserExists() {
        UserEntity existingUser = createValidUserWithId();
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        doNothing().when(userRepository).delete(existingUser);
        userService.deleteUserById(1L);
        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    void deleteUserByUsername_ShouldDeleteUser_WhenUserExists() {
        UserEntity existingUser = createValidUserWithId();
        when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(Optional.of(existingUser));
        doNothing().when(userRepository).delete(existingUser);
        userService.deleteUserByUsername(existingUser.getUsername());
        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    void deleteUserByEmail_ShouldDeleteUser_WhenUserExists() {
        UserEntity existingUser = createValidUserWithId();
        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));
        doNothing().when(userRepository).delete(existingUser);
        userService.deleteUserByEmail(existingUser.getEmail());
        verify(userRepository, times(1)).delete(existingUser);
    }
}
