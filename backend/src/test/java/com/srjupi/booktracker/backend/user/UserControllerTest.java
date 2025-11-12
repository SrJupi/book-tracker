package com.srjupi.booktracker.backend.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srjupi.booktracker.backend.api.dto.UserDTO;
import com.srjupi.booktracker.backend.user.exceptions.User404Exception;
import com.srjupi.booktracker.backend.user.exceptions.User409Exception;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.srjupi.booktracker.backend.common.datafactory.UserTestDataFactory.*;
import static com.srjupi.booktracker.backend.user.UserConstants.USER_ALREADY_EXISTS;
import static com.srjupi.booktracker.backend.user.UserConstants.USER_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void createUser_ShouldReturn201_WhenUserIsValid() throws Exception {
        UserDTO requestDTO = createValidUserDTO();
        UserDTO responseDTO = createValidUserDTOWithId();

        when(userService.createUser(requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/users/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    void createUser_ShouldReturn409_WhenUserAlreadyExists() throws Exception {
        UserDTO requestDTO = createValidUserDTO();
        when(userService.createUser(requestDTO)).thenThrow(User409Exception.fromUsername(requestDTO.getUsername()));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title"). value(USER_ALREADY_EXISTS))
                .andExpect(jsonPath("$.status").value(CONFLICT.value()));
    }

    @Test
    void createUser_ShouldReturn500_WhenThereIsAnServerError() throws Exception {

        when(userService.createUser(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createValidUserDTO())))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title").value(INTERNAL_SERVER_ERROR.getReasonPhrase()))
                .andExpect(jsonPath("$.status").value(INTERNAL_SERVER_ERROR.value()));
    }

    @Test
    void deleteUser_ShouldReturn204_WhenUserIsDeleted() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_ShouldReturn404_WhenUserDoesNotExist() throws Exception {
        doThrow(User404Exception.fromId(1L)).when(userService).deleteUserById(1L);

        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value(USER_NOT_FOUND))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()));

    }

    @Test
    void getUserById_ShouldReturn200_WhenUserExists() throws Exception {
        Long userId = 1L;
        UserDTO userDTO = createValidUserDTOWithId();

        when(userService.getDtoById(userId)).thenReturn(userDTO);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    void getUserById_ShouldReturn404_WhenUserDoesNotExist() throws Exception {
        Long id = 1L;
        when(userService.getDtoById(anyLong())).thenThrow(User404Exception.fromId(id));

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value(USER_NOT_FOUND))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()));
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
        UserDTO responseDTO = createValidUserDTOWithId();

        when(userService.updateUser(userId, requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    void updateUserById_ShouldReturn404_WhenUserDoesNotExist() throws Exception {
        Long id = 1L;
        UserDTO requestDTO = createValidUserDTO();
        when(userService.updateUser(anyLong(), any())).thenThrow(User404Exception.fromId(id));

        mockMvc.perform(put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value(USER_NOT_FOUND))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()));
    }
}