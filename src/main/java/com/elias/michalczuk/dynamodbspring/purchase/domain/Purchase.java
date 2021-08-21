package com.elias.michalczuk.dynamodbspring.purchase.domain;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "purchase")
@Builder
public class Purchase {

    @Id
    private UUID id;
    @DBRef
    private List<Product> products = new LinkedList<>();


    public Purchase generateId() {
        this.id = UUID.randomUUID();
        return this;
    }
}