package com.example.carros.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -4718789759668567625L;

    public ObjectNotFoundException(String msg) {
        super(msg);
    }
    
    public ObjectNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}