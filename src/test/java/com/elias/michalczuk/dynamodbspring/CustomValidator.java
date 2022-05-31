package com.elias.michalczuk.dynamodbspring;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;

import java.util.function.Function;

import static com.elias.michalczuk.dynamodbspring.ValidationResult.*;


enum ValidationResult {
    SUCCESS,
    PRICE_TOO_HIGH,
    NAME_TOO_LONG
}

public interface CustomValidator extends Function<Product, ValidationResult> {
    static CustomValidator nameValidator() {
        return product -> product.getName().length() > 7 ? NAME_TOO_LONG : SUCCESS;
    }
    static CustomValidator priceValidator() {
        return  product -> product.getPrice() > 100000 ? PRICE_TOO_HIGH : SUCCESS;
    }
    default CustomValidator and(CustomValidator other) {
        return product -> {
            ValidationResult result = apply(product);
            return result.equals(SUCCESS) ? other.apply(product) : result;
        };
    }
}