package com.ecua3d.quote.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TaskHasNotProductsException extends Exception{
    private HttpStatus httpStatus;
    private String message;

    public TaskHasNotProductsException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

