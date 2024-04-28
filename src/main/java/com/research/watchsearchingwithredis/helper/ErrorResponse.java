package com.research.watchsearchingwithredis.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@Getter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private Integer status;

    private String message;

    private String description;

    public ErrorResponse(HttpStatus status) {
        this.status = status.value();
    }

    public Boolean hasError() {
        return status != HttpStatus.OK.value();
    }

    public static ErrorResponse noError() {
        return new ErrorResponse(HttpStatus.OK);
    }

    public <T> T ifHasError(Supplier<T> supplier) {
        if (hasError()) {
            return supplier.get();
        }
        return null;
    }

    public <T> T ifHasErrorOrElse(Supplier<T> supplier, Supplier<T> otherSupplier) {
        if (hasError()) {
            return supplier.get();
        }
        return otherSupplier.get();
    }

    public void ifHasError(Runnable action) {
        if (hasError()) {
            action.run();
        }
    }
//
//    public ErrorResponse setStatus(HttpStatus status) {
//        this.status = status.value();
//        return this;
//    }
//
//    public ErrorResponse setMessage(String message) {
//        this.message = message;
//        return this;
//    }
//
//    public ErrorResponse setDescription(String description) {
//        this.description = description;
//        return this;
//    }
}

