package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.api.UsersApi;
import com.srjupi.booktracker.backend.api.dto.UserDTO;
import com.srjupi.booktracker.backend.api.dto.UserWithReadingsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class UserController implements UsersApi {

    private final UserService service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserController(UserService userService) {
        this.service = userService;
    }

    @Override
    public ResponseEntity<UserDTO> createUser(UserDTO userDTO) {
        logger.info("POST /users called with body: {}", userDTO);
        UserDTO createdUser = service.createUser(userDTO);
        URI location = URI.create(String.format("/users/%s", createdUser.getId()));
        logger.info("POST /users created user with id: {} at location: {}", createdUser.getId(), location);
        return ResponseEntity.created(location).body(createdUser);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        logger.info("DELETE /users/{} called", id);
        service.deleteUserById(id);
        logger.info("DELETE /users/{} completed", id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(Long id) {
        logger.info("GET /users/{} called", id);
        UserDTO userDTO = service.getDtoById(id);
        logger.info("GET /users/{} returning: {}", id, userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @Override
    public ResponseEntity<UserWithReadingsDTO> getUserWithReadings(Long id) {
        logger.info("GET /users/{}/readings called", id);
        UserWithReadingsDTO userWithReadingsDTO = service.getUserWithReadingsById(id);
        logger.info("GET /users/{}/readings returning: {}", id, userWithReadingsDTO);
        return ResponseEntity.ok(userWithReadingsDTO);
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers() {
        logger.info("GET /users called");
        List<UserDTO> users = service.getUsers();
        logger.info("GET /users returning {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserDTO> updateUserById(Long id, UserDTO userDTO) {
        logger.info("PUT /users/{} called with body: {}", id, userDTO);
        UserDTO updatedUser = service.updateUser(id, userDTO);
        logger.info("PUT /users/{} updated user to: {}", id, updatedUser);
        return ResponseEntity.ok(updatedUser);
    }
}
