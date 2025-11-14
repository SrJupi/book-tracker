package com.srjupi.booktracker.backend.reading;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingRepository extends JpaRepository<ReadingEntity, Long> {

    
}
