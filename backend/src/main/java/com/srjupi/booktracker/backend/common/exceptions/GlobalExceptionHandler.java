package com.srjupi.booktracker.backend.common.exceptions;

import com.srjupi.booktracker.backend.api.dto.ProblemDetail;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookTrackerBaseException.class)
    public ResponseEntity<ProblemDetail> handleBadRequestException(BookTrackerBaseException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(toProblemDetail(ex, request));
    }

    private ProblemDetail toProblemDetail(BookTrackerBaseException ex, HttpServletRequest request) {
        return new ProblemDetail()
                .type(ex.getType())
                .title(ex.getTitle())
                .status(ex.getStatus().value())
                .detail(ex.getDetail())
                .instance(URI.create(request.getRequestURI()));
    }

}
