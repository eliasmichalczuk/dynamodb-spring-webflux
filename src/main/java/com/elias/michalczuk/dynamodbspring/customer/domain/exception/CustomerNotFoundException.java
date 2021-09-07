package com.elias.michalczuk.dynamodbspring.customer.domain.exception;

import com.elias.michalczuk.dynamodbspring.config.DomainException;
import org.springframework.http.HttpStatus;


public class CustomerNotFoundException extends DomainException {
    public CustomerNotFoundException() {
        super("Customer not found", HttpStatus.BAD_REQUEST);
    }
}
