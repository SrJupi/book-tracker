package com.srjupi.booktracker.backend.reading;

import com.srjupi.booktracker.backend.api.ReadingsApi;
import com.srjupi.booktracker.backend.api.dto.FinishReadingRequest;
import com.srjupi.booktracker.backend.api.dto.ReadingDto;
import com.srjupi.booktracker.backend.api.dto.UserWithReadingsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadingController implements ReadingsApi {

    private final ReadingService readingService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ReadingController(ReadingService readingService) {
        this.readingService = readingService;
    }

    @Override
    public ResponseEntity<ReadingDto> addReadingToUser(Long id, ReadingDto readingDto) {
        return null;
    }

    @Override
    public ResponseEntity<ReadingDto> completeReadingForUser(Long userId, Long readingId, FinishReadingRequest finishReadingRequest) {
        return null;
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
