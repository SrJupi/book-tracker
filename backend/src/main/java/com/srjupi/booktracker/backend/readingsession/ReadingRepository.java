package com.srjupi.booktracker.backend.readingsession;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingRepository extends JpaRepository<ReadingEntity, Long> {

    
}
