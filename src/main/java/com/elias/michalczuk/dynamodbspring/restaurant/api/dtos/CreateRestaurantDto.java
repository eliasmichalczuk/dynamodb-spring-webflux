package com.elias.michalczuk.dynamodbspring.restaurant.api.dtos;

import com.elias.michalczuk.dynamodbspring.restaurant.domain.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class CreateRestaurantDto {
    private String name;
    private List<Long> coordinates;

    public Restaurant toRestaurant() {
        return Restaurant.builder()
                .name(name)
                .location(new GeoJsonPoint(coordinates.get(0), coordinates.get(1)))
                .id(UUID.randomUUID())
                .build();
    }
}
