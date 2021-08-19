package com.elias.michalczuk.dynamodbspring.product.repository;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, UUID> {

}
