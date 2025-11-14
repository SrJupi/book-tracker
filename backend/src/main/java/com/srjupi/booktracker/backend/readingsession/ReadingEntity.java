package com.srjupi.booktracker.backend.readingsession;

import com.srjupi.booktracker.backend.book.BookEntity;
import com.srjupi.booktracker.backend.common.base.BaseEntity;
import com.srjupi.booktracker.backend.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReadingEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id", nullable = false)
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = true)
    private UserEntity user;

    @NotNull
    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;
}
