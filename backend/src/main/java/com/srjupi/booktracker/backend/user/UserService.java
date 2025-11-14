package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.api.dto.UserDTO;
import com.srjupi.booktracker.backend.api.dto.UserWithReadingsDTO;
import com.srjupi.booktracker.backend.user.exceptions.User400Exception;
import com.srjupi.booktracker.backend.user.exceptions.User404Exception;
import com.srjupi.booktracker.backend.user.exceptions.User409Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO dto) {
        logger.info("createUser called");
        if (dto.getUsername() == null) {
            logger.info("Username is null. Throwing 400 Exception.");
            throw User400Exception.fromMissingUsername();
        }
        if (dto.getEmail() == null) {
            logger.info("Email is null. Throwing 400 Exception.");
            throw User400Exception.fromMissingEmail();
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            logger.info("User with email: {} already exists. Throwing 409 Exception.", dto.getEmail());
            throw User409Exception.fromEmail(dto.getEmail());
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            logger.info("User with username: {} already exists. Throwing 409 Exception.", dto.getUsername());
            throw User409Exception.fromUsername(dto.getUsername());
        }
        UserEntity user = mapper.toEntity(dto);
        return mapper.toDTO(userRepository.save(user));
    }

    public UserDTO updateUser(Long id, UserDTO updatedData) {
        logger.info("updateUser called with id: {}", id);
        UserEntity existingUser = getUserById(id);
        existingUser.setUsername(updatedData.getUsername());
        existingUser.setEmail(updatedData.getEmail());
        return mapper.toDTO(userRepository.save(existingUser));
    }

    public List<UserDTO> getUsers() {
        logger.info("getUsers called");
        return mapper.toDTO(userRepository.findAll());
    }

    public UserDTO getDtoById(Long id) {
        logger.info("getDtoById called with id: {}", id);
        UserEntity user = getUserById(id);
        return mapper.toDTO(user);
    }

    private UserEntity getUserById(Long id) {
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

    public UserWithReadingsDTO getUserWithReadingsById(Long id) {
        logger.info("getUserWithReadingsById called with id: {}", id);
        UserEntity user = userRepository.findByIdWithReadings(id).orElseThrow(() -> {
            logger.info("User with id: {} not found. Throwing 404 Exception.", id);
            return User404Exception.fromId(id);
        });
        return mapper.toUserWithReadingsDTO(user);
    }
}
