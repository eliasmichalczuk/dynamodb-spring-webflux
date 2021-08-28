package com.elias.michalczuk.dynamodbspring.restaurant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(collection = "restaurant")
@Builder
public class Restaurant {

    @Id
    private UUID id;
    private String name;
    private GeoJsonPoint location;
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

    public Restaurant generateId() {
        this.id = UUID.randomUUID();
        return this;
    }
}
