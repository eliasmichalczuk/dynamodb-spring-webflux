package com.elias.michalczuk.dynamodbspring.product.repository;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@RedisHash("Product")
public class RedisProduct implements Serializable {
    private String id;
    private String name;
    private Long price;

    @JsonCreator
    RedisProduct(@JsonProperty("id") UUID id,
                 @JsonProperty("name") String name,
                 @JsonProperty("price") Long price) {
        this.id = id.toString();
        this.name = name;
        this.price = price;
    }

    public Product toProduct() {
        return new Product(UUID.fromString(id), name, price);
    }

    public static RedisProduct fromProduct(Product p) {
        return new RedisProduct(p.getId(), p.getName(), p.getPrice());
    }
}
