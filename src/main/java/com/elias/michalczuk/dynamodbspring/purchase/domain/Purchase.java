package com.elias.michalczuk.dynamodbspring.purchase.domain;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private List<UUID> products = new LinkedList<>();
    @Field
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();


    public Purchase generateId() {
        this.id = UUID.randomUUID();
        return this;
    }
}