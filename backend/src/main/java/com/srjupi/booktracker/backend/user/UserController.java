package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.api.UsersApi;
import com.srjupi.booktracker.backend.api.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class UserController implements UsersApi {

    private final UserService service;
    private final UserMapper mapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.service = userService;
        this.mapper = userMapper;
    }

    @Override
    public ResponseEntity<UserDTO> createUser(UserDTO userDTO) {
        UserDTO createdUser = mapper.toDTO(service.createUser(mapper.toEntity(userDTO)));
        URI location = URI.create(String.format("/users/%s", createdUser.getId()));
        return ResponseEntity.created(location).body(createdUser);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(Long id) {
        return ResponseEntity.ok(mapper.toDTO(service.getUserById(id)));
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(mapper.toDTO(service.getUsers()));
    }

    @Override
    public ResponseEntity<UserDTO> updateUserById(Long id, UserDTO userDTO) {
        return ResponseEntity.ok(mapper.toDTO(service.updateUser(id, mapper.toEntity(userDTO))));
    }
}
