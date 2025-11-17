package com.srjupi.booktracker.backend.user;

import com.srjupi.booktracker.backend.api.dto.UserDto;
import com.srjupi.booktracker.backend.api.dto.UserWithReadingsDto;
import com.srjupi.booktracker.backend.reading.ReadingMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReadingMapper.class})
public interface UserMapper {
    UserEntity toEntity(UserDto dto);
    UserDto toDto(UserEntity entity);
    UserWithReadingsDto toUserWithReadingsDto(UserEntity entity);
    List<UserDto> toDto(List<UserEntity> entities);
    List<UserEntity> toEntity(List<UserDto> dtos);
}
