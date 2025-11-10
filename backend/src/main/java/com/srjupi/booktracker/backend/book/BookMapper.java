package com.srjupi.booktracker.backend.book;

import com.srjupi.booktracker.backend.api.dto.BookDTO;
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
    BookEntity toEntity(BookDTO dto);

    @Mapping(target = "authors", expression = "java(stringToList(entity.getAuthors()))")
    @Mapping(target = "coverImageUrl", expression = "java(stringToURI(entity.getCoverImageUrl()))")
    BookDTO toDTO(BookEntity entity);
    List<BookEntity> toEntity(List<BookDTO> dtos);
    List<BookDTO> toDTO(List<BookEntity> entities);

    BookPage toDTO(Page<BookEntity> entityPage);

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
