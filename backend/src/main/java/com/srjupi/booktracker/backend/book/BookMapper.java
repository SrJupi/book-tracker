package com.srjupi.booktracker.backend.book;

import com.srjupi.booktracker.backend.api.dto.BookDto;
import com.srjupi.booktracker.backend.api.dto.BookPage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.net.URI;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "authors", expression = "java(listToString(dto.getAuthors()))")
    @Mapping(target = "coverImageUrl", expression = "java(uriToString(dto.getCoverImageUrl()))")
    BookEntity toEntity(BookDto dto);

    @Mapping(target = "authors", expression = "java(stringToList(entity.getAuthors()))")
    @Mapping(target = "coverImageUrl", expression = "java(stringToURI(entity.getCoverImageUrl()))")
    BookDto toDto(BookEntity entity);
    List<BookEntity> toEntity(List<BookDto> dtos);
    List<BookDto> toDto(List<BookEntity> entities);

    BookPage toDto(Page<BookEntity> entityPage);

    default List<String> stringToList(String authors) {
        return List.of(authors.split(","));
    }

    default String listToString(List<String> authors) {
        return String.join(",", authors);
    }

    default URI stringToURI(String uri) {
        return uri != null ? URI.create(uri) : null;
    }

    default String uriToString(URI uri) {
        return uri != null ? uri.toString() : null;
    }
}
