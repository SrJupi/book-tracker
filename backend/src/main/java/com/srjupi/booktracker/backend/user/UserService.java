package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.user.exceptions.User404Exception;
import com.srjupi.booktracker.backend.user.exceptions.User409Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity user) {
        logger.info("createUser called");
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.info("User with email: {} already exists. Throwing 409 Exception.", user.getEmail());
            throw User409Exception.fromEmail(user.getEmail());
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            logger.info("User with username: {} already exists. Throwing 409 Exception.", user.getUsername());
            throw User409Exception.fromUsername(user.getUsername());
        }
        return userRepository.save(user);
    }

    public UserEntity updateUser(Long id, UserEntity updatedData) {
        logger.info("updateUser called with id: {}", id);
        UserEntity existingUser = getUserById(id);
        existingUser.setUsername(updatedData.getUsername());
        existingUser.setEmail(updatedData.getEmail());
        return userRepository.save(existingUser);
    }

    public List<UserEntity> getUsers() {
        logger.info("getUsers called");
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        logger.info("getUserById called with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("User with id: {} not found. Throwing 404 Exception.", id);
                    return User404Exception.fromId(id);
                });
    }

    public UserEntity getUserByUsername(String username) {
        logger.info("getUserByUsername called with username: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.info("User with username: {} not found. Throwing 404 Exception.", username);
                    return User404Exception.fromUsername(username);
                });
    }

    public UserEntity getUserByEmail(String email) {
        logger.info("getUserByEmail called with email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.info("User with email: {} not found. Throwing 404 Exception.", email);
                    return User404Exception.fromEmail(email);
                });
    }

    public void deleteUserById(Long id) {
        logger.info("deleteUserById called with id: {}", id);
        UserEntity user = getUserById(id);
        userRepository.delete(user);
    }

    public void deleteUserByUsername(String username) {
        logger.info("deleteUserByUsername called with username: {}", username);
        UserEntity user = getUserByUsername(username);
        userRepository.delete(user);
    }

    public void deleteUserByEmail(String email) {
        logger.info("deleteUserByEmail called with email: {}", email);
        UserEntity user = getUserByEmail(email);
        userRepository.delete(user);
    }
}
