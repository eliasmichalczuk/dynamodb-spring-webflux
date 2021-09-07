package com.elias.michalczuk.dynamodbspring.restaurant.repository;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import com.elias.michalczuk.dynamodbspring.restaurant.domain.Restaurant;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface RestaurantRepository extends ReactiveMongoRepository<Restaurant, UUID> {

    @Query("{\n" +
            "   location: {\n" +
            "     $near: {\n" +
            "       $geometry: {\n" +
            "          type: \"Point\" ,\n" +
            "          coordinates: [ ?1 ,?2 ]\n" +
            "       },\n" +
            "       $maxDistance: ?0\n" +
            "     }\n" +
            "   }\n" +
            "}")
    Flux<Restaurant> findNear(Long maxDistance, Long longitude, Long latitude);
}
