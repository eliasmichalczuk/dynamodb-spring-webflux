package com.elias.michalczuk.dynamodbspring.customer.repository;

import com.elias.michalczuk.dynamodbspring.customer.domain.Customer;
import com.elias.michalczuk.dynamodbspring.restaurant.domain.Restaurant;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, UUID> {
}
