package com.srjupi.booktracker.backend.reading;

import com.srjupi.booktracker.backend.api.dto.FinishReadingRequest;
import com.srjupi.booktracker.backend.api.dto.ReadingCreateRequest;
import com.srjupi.booktracker.backend.api.dto.ReadingDto;
import com.srjupi.booktracker.backend.book.BookEntity;
import com.srjupi.booktracker.backend.book.BookRepository;
import com.srjupi.booktracker.backend.book.exceptions.Book404Exception;
import com.srjupi.booktracker.backend.reading.exceptions.Reading404Exception;
import com.srjupi.booktracker.backend.user.UserEntity;
import com.srjupi.booktracker.backend.user.UserMapper;
import com.srjupi.booktracker.backend.user.UserRepository;
import com.srjupi.booktracker.backend.user.exceptions.User404Exception;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReadingService {

    private final ReadingRepository repository;
    private final ReadingMapper mapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReadingService(ReadingRepository repository, ReadingMapper mapper,
                          UserRepository userRepository, BookRepository bookRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }


    public ReadingDto addReadingToUser(Long id, ReadingCreateRequest readingRequest) {
        UserEntity user = userRepository
                .findById(id)
                .orElseThrow(()-> User404Exception.fromId(id));
        BookEntity book = bookRepository
                .findById(readingRequest.getBookId())
                .orElseThrow(() -> Book404Exception.fromId(readingRequest.getBookId()));
        ReadingEntity reading = new ReadingEntity();
        reading.setUser(user);
        reading.setBook(book);
        LocalDate startedAt = readingRequest.getStartedAt() != null ?
                readingRequest.getStartedAt() : LocalDate.now();
        reading.setStartedAt(startedAt);
        reading.setFinishedAt(readingRequest.getFinishedAt());
        return mapper.toDTO(repository.save(reading));
    }

    @Transactional
    public ReadingDto completeReadingForUser(Long userId, Long readingId, FinishReadingRequest finishReadingRequest) {
        ReadingEntity reading = repository.findById(readingId)
                .orElseThrow(() -> Reading404Exception.fromId(readingId));
        /*
        Verify that the reading belongs to the user when JWT is implemented
         if (!reading.getUser().getId().equals(authenticatedUserId)) {
             throw new UnauthorizedException("User is not authorized to complete this reading.");
         }
         */
        LocalDate finishedAt = finishReadingRequest.getFinishedAt() != null ?
                finishReadingRequest.getFinishedAt() : LocalDate.now();
        reading.setFinishedAt(finishedAt);
        return mapper.toDTO(repository.save(reading));
    }
}
