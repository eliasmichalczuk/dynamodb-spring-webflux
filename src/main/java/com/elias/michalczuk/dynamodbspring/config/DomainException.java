package com.elias.michalczuk.dynamodbspring.config;

import org.springframework.http.HttpStatus;

public class DomainException extends RuntimeException{
    private static final long serialVersionUID = -7661881974219233311L;

    private HttpStatus status;

    public DomainException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatusCode() {
        return status;
    }
}
