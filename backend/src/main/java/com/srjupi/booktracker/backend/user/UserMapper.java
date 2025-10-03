package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.api.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(UserDTO dto);
    UserDTO toDTO(UserEntity entity);
    List<UserDTO> toDTO(List<UserEntity> entities);
    List<UserEntity> toEntity(List<UserDTO> dtos);
}
