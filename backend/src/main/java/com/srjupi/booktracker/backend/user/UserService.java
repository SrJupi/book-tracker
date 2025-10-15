package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.user.exceptions.User404Exception;
import com.srjupi.booktracker.backend.user.exceptions.User409Exception;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw User409Exception.fromEmail(user.getEmail());
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw User409Exception.fromUsername(user.getUsername());
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
                .orElseThrow(() -> User409Exception.fromUsername(username));
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> User409Exception.fromEmail(email));
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
