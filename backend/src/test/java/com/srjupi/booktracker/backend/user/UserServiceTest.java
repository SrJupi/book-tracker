package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.api.dto.UserDto;
import com.srjupi.booktracker.backend.user.exceptions.User404Exception;
import com.srjupi.booktracker.backend.user.exceptions.User409Exception;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.srjupi.booktracker.backend.common.datafactory.UserTestDataFactory.*;
import static com.srjupi.booktracker.backend.user.UserConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    void createUser_ShouldCreateUser_WhenUserIsValid() {
        UserEntity user = createValidUser();
        UserDto requestDto = createValidUserDto();
        UserDto responseDto = createValidUserDtoWithId();
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(createValidUserWithId());
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(user);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(responseDto);
        UserDto createdUser = userService.createUser(requestDto);
        assertNotNull(createdUser);
        assertEquals(1L, createdUser.getId());
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser_ShouldThrowException_WhenEmailAlreadyExists() {
        UserDto requestDto = createValidUserDto();
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        Exception exception = assertThrows(User409Exception.class, () -> userService.createUser(requestDto));
        assertEquals(String.format(DETAIL_EMAIL_ALREADY_EXISTS, requestDto.getEmail()), exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_ShouldThrowException_WhenUserNameAlreadyExists() {
        UserDto requestDto = createValidUserDto();
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        Exception exception = assertThrows(User409Exception.class, () -> userService.createUser(requestDto));
        assertEquals(String.format(DETAIL_USERNAME_ALREADY_EXISTS, requestDto.getUsername()), exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        UserEntity existingUser = createValidUserWithId();
        UserDto requestDto = createValidUserDto();
        UserDto responseDto = createValidUserDtoWithId();
        responseDto.setUsername("newusername");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(userMapper.toDto(existingUser)).thenReturn(responseDto);
        UserDto updatedUser = userService.updateUser(1L, requestDto);
        assertNotNull(updatedUser);
        assertEquals(existingUser.getId(), updatedUser.getId());
        assertEquals("newusername", updatedUser.getUsername());
        assertEquals(existingUser.getEmail(), updatedUser.getEmail());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(User404Exception.class, () -> userService.updateUser(id, createValidUserDto()));
        assertEquals(String.format(DETAIL_NOT_FOUND_BY_ID, id), exception.getMessage());
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
        UserDto responseDto = createValidUserDtoWithId();
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userMapper.toDto(existingUser)).thenReturn(responseDto);
        UserDto user = userService.getDtoById(1L);
        assertNotNull(user);
        assertEquals(existingUser.getId(), user.getId());
        assertEquals(existingUser.getUsername(), user.getUsername());
        assertEquals(existingUser.getEmail(), user.getEmail());
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserDoesNotExist() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(User404Exception.class, () -> userService.getDtoById(id));
        assertEquals(String.format(DETAIL_NOT_FOUND_BY_ID, id), exception.getMessage());
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
        String wrongUsername = "nonexistentuser";
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(User404Exception.class, () -> userService.getUserByUsername(wrongUsername));
        assertEquals(String.format(DETAIL_NOT_FOUND_BY_USERNAME, wrongUsername), exception.getMessage());
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
        String wrongEmail = "abc@xyz.com";
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(User404Exception.class, () -> userService.getUserByEmail(wrongEmail));
        assertEquals(String.format(DETAIL_NOT_FOUND_BY_EMAIL, wrongEmail), exception.getMessage());
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
