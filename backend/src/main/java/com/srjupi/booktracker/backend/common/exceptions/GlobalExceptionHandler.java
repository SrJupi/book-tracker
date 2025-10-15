package com.srjupi.booktracker.backend.common.exceptions;

import com.srjupi.booktracker.backend.api.dto.ProblemDetail;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final HashMap<Class<? extends BookTrackerBaseException>, HttpStatus> exceptionHttpStatusMap = new HashMap<>() {{
        put(BookTracker400Exception.class, BAD_REQUEST);
        put(BookTracker404Exception.class, NOT_FOUND);
        put(BookTracker409Exception.class, CONFLICT);
        put(BookTracker500Exception.class, INTERNAL_SERVER_ERROR);
    }};

    @ExceptionHandler(BookTrackerBaseException.class)
    public ResponseEntity<ProblemDetail> handleBookTrackerException(BookTrackerBaseException ex, HttpServletRequest request) {
        HttpStatus status = exceptionHttpStatusMap.getOrDefault(ex.getClass().getSuperclass(), INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(status).body(toProblemDetail(ex, request.getRequestURI(), status));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex, HttpServletRequest request) {
        //Log the exception when logger is available

        ProblemDetail problemDetail = new ProblemDetail()
                .type(URI.create("/errors/internal"))
                .title("Internal Server Error")
                .status(INTERNAL_SERVER_ERROR.value())
                .detail("Oh oh, something went wrong on our side. Please try again later.")
                .instance(URI.create(request.getRequestURI()));
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    private ProblemDetail toProblemDetail(BookTrackerBaseException ex, String requestUri, HttpStatus status) {
        return new ProblemDetail()
                .type(ex.getType())
                .title(ex.getTitle())
                .status(status.value())
                .detail(ex.getDetail())
                .instance(URI.create(requestUri));
    }

}
