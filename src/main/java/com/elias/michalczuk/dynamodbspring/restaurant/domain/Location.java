package com.elias.michalczuk.dynamodbspring.restaurant.domain;

import com.mongodb.client.model.geojson.GeoJsonObjectType;
import com.mongodb.client.model.geojson.Point;
import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Location {

    @Builder.Default
    private GeoJsonObjectType type = GeoJsonObjectType.POINT;
    private List<Long> coordinates;
}
