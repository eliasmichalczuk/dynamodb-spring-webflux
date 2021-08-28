package com.elias.michalczuk.dynamodbspring.restaurant.repository;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import com.elias.michalczuk.dynamodbspring.restaurant.domain.Restaurant;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantRepository extends ReactiveMongoRepository<Restaurant, UUID> {

}
