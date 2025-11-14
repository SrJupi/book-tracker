package com.srjupi.booktracker.backend.reading;

import com.srjupi.booktracker.backend.api.dto.ReadingDto;
import com.srjupi.booktracker.backend.book.BookMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface ReadingMapper {
    ReadingEntity toEntity(ReadingDto dto);
    ReadingDto toDTO(ReadingEntity entity);
}
