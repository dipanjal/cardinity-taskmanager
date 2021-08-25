package com.cardinity.assessment.advice;

import com.cardinity.assessment.exception.AuthException;
import com.cardinity.assessment.exception.BadRequestException;
import com.cardinity.assessment.exception.RecordNotFoundException;
import com.cardinity.assessment.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestExceptionHandler(BadRequestException ex, WebRequest request) {
        this.logException(ex);
        return this.buildResponseEntity(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> recordNotFoundExceptionHandler(RecordNotFoundException ex, WebRequest request) {
        this.logException(ex);
        return this.buildResponseEntity(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> authExceptionHandler(AuthException ex, WebRequest request) {
        this.logException(ex);
        return this.buildResponseEntity(HttpStatus.UNAUTHORIZED, ex);
    }

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseBuilder
                        .buildResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"));
    }*/

    private ResponseEntity<?> buildResponseEntity(HttpStatus status, Exception ex){
        return ResponseEntity
                .status(status)
                .body(ResponseBuilder.buildResponse(status, ex.getLocalizedMessage()));
    }

    private void logException(Exception e){
        log.error(e.getMessage());
    }
}
