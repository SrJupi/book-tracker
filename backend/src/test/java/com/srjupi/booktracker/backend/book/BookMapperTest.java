package com.srjupi.booktracker.backend.book;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.util.List;

public class BookMapperTest {

    private final BookMapper mapper = Mappers.getMapper(BookMapper.class);
    private final String authorsString = "Author1,Author2,Author3";
    private final List<String> authorsList = List.of("Author1", "Author2", "Author3");
    private final String uriString = "http://example.com/cover.jpg";
    private final URI uri = URI.create(uriString);

    @Test
    void stringToList() {
        List<String> result = mapper.stringToList(authorsString);
        assert result.equals(authorsList);
    }

    @Test
    void listToString() {
        String result = mapper.listToString(authorsList);
        assert result.equals(authorsString);
    }

    @Test
    void stringToURI_whenStringNotNull_returnsURI() {
        URI result = mapper.stringToURI(uriString);
        assert result.equals(uri);
    }

    @Test
    void stringToURI_whenStringNull_returnsNull() {
        URI result = mapper.stringToURI(null);
        assert result == null;
    }

    @Test
    void uriToString_whenUriNotNull_returnsString() {
        String result = mapper.uriToString(uri);
        assert result.equals(uriString);
    }

    @Test
    void uriToString_whenUriNull_returnsNull() {
        String result = mapper.uriToString(null);
        assert result == null;
    }

}
