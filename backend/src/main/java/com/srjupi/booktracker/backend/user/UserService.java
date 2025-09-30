package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.common.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<UserEntity, Long> {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    // Placeholder using UserEntity while there is no DTO
    // Change to UserDTO when it is created
    // Exceptions should also be changed when a proper exception handling strategy is in place
    public UserEntity createUser(UserEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with given email already exists");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("User with given username already exists");
        }
        return save(user);
    }

    public UserEntity updateUser(String username, UserEntity updatedData) {
        UserEntity existingUser = getUserByUsername(username);
        existingUser.setUsername(updatedData.getUsername());
        existingUser.setEmail(updatedData.getEmail());
        return save(existingUser);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void deleteUserByUsername(String username) {
        UserEntity user = getUserByUsername(username);
        userRepository.delete(user);
    }

    public void deleteUserByEmail(String email) {
        UserEntity user = getUserByEmail(email);
        userRepository.delete(user);
    }
}
