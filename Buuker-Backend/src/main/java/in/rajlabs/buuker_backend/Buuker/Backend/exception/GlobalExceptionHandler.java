package in.rajlabs.buuker_backend.Buuker.Backend.exception;

import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Global exception handler to manage exceptions across the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Helper method to construct error response
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", Instant.now().toEpochMilli());
        errorDetails.put("message", message);
        errorDetails.put("status", status.value());
        return new ResponseEntity<>(errorDetails, status);
    }

    /**
     * Handles ResourceNotFoundException.
     *
     * @param ex ResourceNotFoundException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        logger.warn("Resource not found: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Handles ValidationException.
     *
     * @param ex ValidationException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException ex) {
        logger.warn("Validation error: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Handles HttpMessageNotReadableException.
     *
     * @param ex      HttpMessageNotReadableException
     * @param request WebRequest
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        String message = "Invalid JSON format or missing required fields. " + ex.getMessage();
        logger.warn("Message not readable: {}", message);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }
    /**
     * Handles MethodArgumentNotValidException for validation errors.
     *
     * @param ex MethodArgumentNotValidException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", Instant.now().toEpochMilli());
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());

        // Collecting error messages
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        errorDetails.put("errors", errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<?> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", Instant.now().toEpochMilli());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("status", HttpStatus.CONFLICT.value()); // 409 Conflict
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    /**
     * Handles all other exceptions.
     *
     * @param ex Exception
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        logger.error("Internal server error: {}", ex.getMessage(), ex);

        var err_res = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        Objects.requireNonNull(err_res.getBody()).put("type", ex.getClass().getSimpleName());
        return err_res;
    }

}
