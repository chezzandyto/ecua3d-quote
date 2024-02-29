package com.ecua3d.quote.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class EntityExistsException extends Exception{
    private HttpStatus httpStatus;
    private String message;
}

