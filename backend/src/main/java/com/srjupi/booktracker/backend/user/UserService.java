package com.srjupi.booktracker.backend.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Exceptions should also be changed when a proper exception handling strategy is in place
    public UserEntity createUser(UserEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with given email already exists");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("User with given username already exists");
        }
        return userRepository.save(user);
    }

    public UserEntity updateUser(Long id, UserEntity updatedData) {
        UserEntity existingUser = getUserById(id);
        existingUser.setUsername(updatedData.getUsername());
        existingUser.setEmail(updatedData.getEmail());
        return userRepository.save(existingUser);
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> User404Exception.fromId(id));
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void deleteUserById(Long id) {
        UserEntity user = getUserById(id);
        userRepository.delete(user);
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
