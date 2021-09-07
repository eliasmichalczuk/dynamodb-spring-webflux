package com.elias.michalczuk.dynamodbspring.purchase.api.dto;

import com.elias.michalczuk.dynamodbspring.customer.domain.Customer;
import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class PurchaseGetAllDto {

    private List<Product> products;
    private Customer customer;

    public static PurchaseGetAllDto of(List<Product> products, Customer customer) {
        return new PurchaseGetAllDto(products, customer);
    }
}
