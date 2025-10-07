package com.srjupi.booktracker.backend.book;

import com.srjupi.booktracker.backend.api.dto.BookDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookEntity toEntity(BookDTO dto);
    BookDTO toDTO(BookEntity entity);
    List<BookEntity> toEntity(List<BookDTO> dtos);
    List<BookDTO> toDTO(List<BookEntity> entities);
}
