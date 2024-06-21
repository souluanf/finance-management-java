package dev.luanfernandes.config.web;

import static dev.luanfernandes.constants.ExceptionHandlerConstants.STACKTRACE_PROPERTY;
import static dev.luanfernandes.constants.ExceptionHandlerConstants.TIMESTAMP_PROPERTY;
import static java.lang.String.format;
import static java.time.LocalTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ProblemDetail problemDetail = forStatusAndDetail(BAD_REQUEST, "Validation failed for argument");
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> format("%s: %s", error.getField(), error.getDefaultMessage()))
                .toList();
        problemDetail.setProperty(TIMESTAMP_PROPERTY, Instant.now());
        problemDetail.setProperty(STACKTRACE_PROPERTY, errors);
        return problemDetail;
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    ProblemDetail handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException exception) {
        return exceptionToProblemDetailForStatusAndDetail(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ProblemDetail handleHttpClientErrorExceptionNotFound(ResourceAccessException exception) {
        return exceptionToProblemDetailForStatusAndDetail(SERVICE_UNAVAILABLE, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return exceptionToProblemDetailForStatusAndDetail(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException exception) {
        return exceptionToProblemDetailForStatusAndDetail(CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return exceptionToProblemDetailForStatusAndDetail(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleResponseStatusException(ResponseStatusException exception) {
        return exceptionToProblemDetailForStatusAndDetail(exception.getStatusCode(), exception.getReason());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException exception) {
        return exceptionToProblemDetailForStatusAndDetail(NOT_FOUND, exception.getMessage());
    }

    private ProblemDetail exceptionToProblemDetailForStatusAndDetail(HttpStatusCode status, String detail) {
        ProblemDetail problemDetail = forStatusAndDetail(status, detail);
        problemDetail.setProperty(TIMESTAMP_PROPERTY, now());
        return problemDetail;
    }
}
