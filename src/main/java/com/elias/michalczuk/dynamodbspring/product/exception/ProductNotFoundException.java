package com.elias.michalczuk.dynamodbspring.product.exception;

import com.elias.michalczuk.dynamodbspring.config.DomainException;
import org.springframework.http.HttpStatus;


public class ProductNotFoundException extends DomainException {
    public ProductNotFoundException() {
        super("Product not found", HttpStatus.BAD_REQUEST);
    }
}
