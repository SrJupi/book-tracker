package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.api.UsersApi;
import com.srjupi.booktracker.backend.api.dto.UserDto;
import com.srjupi.booktracker.backend.api.dto.UserWithReadingsDto;
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
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        logger.info("POST /users called with body: {}", userDto);
        UserDto createdUser = service.createUser(userDto);
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
    public ResponseEntity<UserDto> getUserById(Long id) {
        logger.info("GET /users/{} called", id);
        UserDto userDto = service.getDtoById(id);
        logger.info("GET /users/{} returning: {}", id, userDto);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<UserWithReadingsDto> getUserWithReadings(Long id) {
        logger.info("GET /users/{}/readings called", id);
        UserWithReadingsDto userWithReadingsDto = service.getUserWithReadingsById(id);
        logger.info("GET /users/{}/readings returning: {}", id, userWithReadingsDto);
        return ResponseEntity.ok(userWithReadingsDto);
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        logger.info("GET /users called");
        List<UserDto> users = service.getUsers();
        logger.info("GET /users returning {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserDto> updateUserById(Long id, UserDto userDto) {
        logger.info("PUT /users/{} called with body: {}", id, userDto);
        UserDto updatedUser = service.updateUser(id, userDto);
        logger.info("PUT /users/{} updated user to: {}", id, updatedUser);
        return ResponseEntity.ok(updatedUser);
    }
}
