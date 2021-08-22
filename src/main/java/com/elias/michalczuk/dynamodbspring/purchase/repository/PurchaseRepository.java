package com.elias.michalczuk.dynamodbspring.purchase.repository;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import com.elias.michalczuk.dynamodbspring.purchase.domain.Purchase;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends ReactiveMongoRepository<Purchase, UUID> {

    @Query("{ $and: [{ 'date' : {$gte: ?0} }, { 'date' : {$lte: ?1} }]}")
    Flux<Purchase> findByDataDeAte(LocalDateTime de, LocalDateTime ate);

    Flux<Purchase> findByDateBetween(LocalDateTime de, LocalDateTime ate);
}
