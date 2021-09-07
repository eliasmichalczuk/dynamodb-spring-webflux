package com.elias.michalczuk.dynamodbspring.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Document
@Builder
@Getter
public class Customer {

    @Id
    private UUID id;
    private String name;
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

    public Customer generateId() {
        this.id = UUID.randomUUID();
        return this;
    }
}
