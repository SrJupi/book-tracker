package com.srjupi.booktracker.backend.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srjupi.booktracker.backend.api.dto.UserDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.srjupi.booktracker.backend.common.datafactory.UserTestDataFactory.*;
import static com.srjupi.booktracker.backend.common.datafactory.UserTestDataFactory.createValidUserDTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserMapper userMapper;

    @Test
    void createUser_ShouldReturn201_WhenUserIsValid() throws Exception {
        UserDTO requestDTO = createValidUserDTO();
        UserEntity user = createValidUser();
        UserEntity createdUser = createValidUserWithId();
        UserDTO responseDTO = createValidUserDTOWithId();

        when(userMapper.toEntity(requestDTO)).thenReturn(user);
        when(userService.createUser(user)).thenReturn(createdUser);
        when(userMapper.toDTO(createdUser)).thenReturn(responseDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/users/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Disabled("TODO: Add error handling test when GlobalExceptionHandler is implemented")
    @Test
    void createUser_ShouldReturn409_WhenUserAlreadyExists() {
    }

    @Disabled("TODO: Add error handling test when GlobalExceptionHandler is implemented")
    @Test
    void createUser_ShouldReturn500_WhenThereIsAnServerError() {
    }

    @Test
    void deleteUser_ShouldReturn204_WhenUserIsDeleted() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Disabled("TODO: Add error handling test when GlobalExceptionHandler is implemented")
    @Test
    void deleteUser_ShouldReturn404_WhenUserDoesNotExist() {
    }

    @Test
    void getUserById_ShouldReturn200_WhenUserExists() throws Exception {
        Long userId = 1L;
        UserEntity user = createValidUserWithId();
        UserDTO userDTO = createValidUserDTOWithId();

        when(userService.getUserById(userId)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Disabled("TODO: Add error handling test when GlobalExceptionHandler is implemented")
    @Test
    void getUserById_ShouldReturn404_WhenUserDoesNotExist() {
    }

    @Test
    void getUsers_ShouldReturn200_Always() throws Exception{
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserById_shouldReturn200_WhenUserIsUpdated() throws Exception {
        Long userId = 1L;
        UserDTO requestDTO = createValidUserDTO();
        UserEntity user = createValidUser();
        UserEntity updatedUser = createValidUserWithId();
        UserDTO responseDTO = createValidUserDTOWithId();

        when(userMapper.toEntity(requestDTO)).thenReturn(user);
        when(userService.updateUser(userId, user)).thenReturn(updatedUser);
        when(userMapper.toDTO(updatedUser)).thenReturn(responseDTO);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Disabled("TODO: Add error handling test when GlobalExceptionHandler is implemented")
    @Test
    void updateUserById_ShouldReturn404_WhenUserDoesNotExist() {
    }
}