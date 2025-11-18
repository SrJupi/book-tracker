package com.srjupi.booktracker.backend.reading;

import com.srjupi.booktracker.backend.api.ReadingsApi;
import com.srjupi.booktracker.backend.api.dto.FinishReadingRequest;
import com.srjupi.booktracker.backend.api.dto.ReadingCreateRequest;
import com.srjupi.booktracker.backend.api.dto.ReadingDto;
import com.srjupi.booktracker.backend.api.dto.UserWithReadingsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ReadingController implements ReadingsApi {

    private final ReadingService readingService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ReadingController(ReadingService readingService) {
        this.readingService = readingService;
    }

    @Override
    public ResponseEntity<ReadingDto> addReadingToUser(Long id, ReadingCreateRequest readingRequest) {
        logger.info("POST /users/{}/readings called with body: {}", id, readingRequest);
        ReadingDto response = readingService.addReadingToUser(id, readingRequest);
        URI location = URI.create(String.format("/users/%s/readings/%s", id, response.getId()));
        logger.info("POST /users/{}/readings completed at location {}", response.getId(), location);
        return ResponseEntity.created(location).body(response);
    }

    @Override
    public ResponseEntity<ReadingDto> completeReadingForUser(Long userId, Long readingId, FinishReadingRequest finishReadingRequest) {
        logger.info("POST /users/{}/readings/{}/finish called with body: {}", userId, readingId, finishReadingRequest);
        ReadingDto response = readingService.completeReadingForUser(userId, readingId, finishReadingRequest);
        logger.info("POST /users/{}/readings/{}/finish completed", userId, readingId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteReadingFromUser(Long userId, Long readingId) {
        return null;
    }

    @Override
    public ResponseEntity<ReadingDto> getReadingOfUser(Long userId, Long readingId) {
        return null;
    }

    @Override
    public ResponseEntity<ReadingDto> updateReadingOfUser(Long userId, Long readingId, ReadingDto readingDto) {
        return null;
    }
}
