package com.elias.michalczuk.dynamodbspring.purchase.repository;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import com.elias.michalczuk.dynamodbspring.purchase.domain.Purchase;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseRepository extends ReactiveMongoRepository<Purchase, UUID> {
}
