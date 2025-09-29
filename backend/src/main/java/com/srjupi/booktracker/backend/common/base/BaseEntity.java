package com.srjupi.booktracker.backend.common.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    private Timestamp updateAt;

    //JPA Callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.from(java.time.Instant.now());
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = Timestamp.from(java.time.Instant.now());
    }


}
