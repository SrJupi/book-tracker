package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.api.UsersApi;
import com.srjupi.booktracker.backend.api.dto.UserDTO;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController implements UsersApi {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<UserDTO> createUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers() {
        return null;
    }
}
