package com.elias.michalczuk.dynamodbspring.product.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Product {

    private UUID id;
    private String name;
    private Long price;

    @DynamoDbPartitionKey()
    @DynamoDbAttribute("id")
    public UUID getId() {
        return id;
    }

    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    @DynamoDbAttribute("price")
    public Long getPrice() {
        return price;
    }

    public Product generateId() {
        this.id = UUID.randomUUID();
        return this;
    }
}
