package com.srjupi.booktracker.backend.book;

import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<BookEntity> hasTitle(String title) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<BookEntity> hasAuthors(String authors) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("authors")), "%" + authors.toLowerCase() + "%");
    }

    public static Specification<BookEntity> hasIsbn(String isbn) {
        return (root, query, builder) ->
                builder.equal(root.get("isbn"), isbn);
    }

    public static Specification<BookEntity> hasPublisher(String publisher) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("publisher")), "%" + publisher.toLowerCase() + "%");
    }

    public static Specification<BookEntity> hasLanguage(String language) {
        return (root, query, builder) ->
                builder.equal(root.get("language"), language);
    }
}
