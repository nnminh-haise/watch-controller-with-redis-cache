package com.research.watchsearchingwithredis.helper;

import com.research.watchsearchingwithredis.exception.ResourceNotFoundException;
import com.research.watchsearchingwithredis.exception.UnexpectedUpdateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleGenericException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.internalServerError(exception.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto<Object>> handleResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseDto.notFound(resourceNotFoundException.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto<Object>> handleDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.badRequest(dataIntegrityViolationException.getMessage()));
    }

    @ExceptionHandler(UnexpectedUpdateException.class)
    public ResponseEntity<ResponseDto<Object>> handleUnexpectedUpdateException(
            UnexpectedUpdateException unexpectedUpdateException){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.badRequest(unexpectedUpdateException.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<Object>> handleIllegalArgumentException(
            IllegalArgumentException illegalArgumentException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.badRequest(illegalArgumentException.getMessage()));
    }
}
