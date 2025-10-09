package com.srjupi.booktracker.backend.book;

import com.srjupi.booktracker.backend.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String authors;

    @Column(unique = true)
    private String isbn;

    private String publisher;

    private String language;

    private String edition;

    @Column(nullable = false)
    private Integer pages;

    private String coverImageUrl;

}
