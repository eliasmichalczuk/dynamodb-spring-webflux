package com.elias.michalczuk.dynamodbspring.product.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Setter
public class Product {

    @Id
    private UUID id;
    private String name;
    private Long price;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public Product generateId() {
        this.id = UUID.randomUUID();
        return this;
    }
}
