package com.srjupi.booktracker.backend.book;

import com.srjupi.booktracker.backend.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookEntity extends BaseEntity {

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private String authors;

    @Column(unique = true)
    private String isbn;

    private String publisher;

    private String language;

    @NotNull
    @Column(nullable = false)
    private Integer pages;

    private String coverImageUrl;

}
