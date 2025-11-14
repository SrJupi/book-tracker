package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.api.dto.UserDTO;
import com.srjupi.booktracker.backend.api.dto.UserWithReadingsDTO;
import com.srjupi.booktracker.backend.reading.ReadingMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReadingMapper.class})
public interface UserMapper {
    UserEntity toEntity(UserDTO dto);
    UserDTO toDTO(UserEntity entity);
    UserWithReadingsDTO toUserWithReadingsDTO(UserEntity entity);
    List<UserDTO> toDTO(List<UserEntity> entities);
    List<UserEntity> toEntity(List<UserDTO> dtos);
}
