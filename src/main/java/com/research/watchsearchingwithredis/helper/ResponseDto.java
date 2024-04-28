package com.research.watchsearchingwithredis.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private Integer status;

    private Optional<T> data;

    private String message;

    public ResponseDto(HttpStatus status, String message) {
        this.status = status.value();
        this.data = Optional.empty();
        this.message = message;
    }

    public ResponseDto(HttpStatus status, T data, String message) {
        this.status = status.value();
        this.data = Optional.of(data);
        this.message = message;
    }

    public static <T> ResponseDto<T> success() {
        return new ResponseDto<>(HttpStatus.OK, "Success!");
    }

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(HttpStatus.OK, data, "Success!");
    }

    public static <T> ResponseDto<T> success(T data, String message) {
        return new ResponseDto<>(HttpStatus.OK, data, message);
    }

    public static <T> ResponseDto<T> badRequest(String message) {
        return new ResponseDto<>(HttpStatus.BAD_REQUEST, message);
    }

    public static <T> ResponseDto<T> notFound(String message) {
        return new ResponseDto<>(HttpStatus.NOT_FOUND, message);
    }

    public static <T> ResponseDto<T> internalServerError(String message) {
        return new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public Boolean hasError() {
        return this.status != HttpStatus.OK.value();
    }

    public ResponseEntity<ResponseDto<T>> buildEntity() {
        return ResponseEntity.status(this.status).body(this);
    }
}
