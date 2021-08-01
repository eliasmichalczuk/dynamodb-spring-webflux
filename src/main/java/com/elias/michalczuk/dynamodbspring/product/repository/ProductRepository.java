package com.elias.michalczuk.dynamodbspring.product.repository;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;

@Repository
public class ProductRepository {

    private final DynamoDbEnhancedAsyncClient enhancedAsyncClient;
    private final DynamoDbAsyncTable<Product> productDynamoDbAsyncTable;

    public ProductRepository(DynamoDbEnhancedAsyncClient asyncClient) {
        this.enhancedAsyncClient = asyncClient;
        this.productDynamoDbAsyncTable = enhancedAsyncClient.table(Product.class.getSimpleName(), TableSchema.fromBean(Product.class));
    }

    public Mono<Void> save(Product product) {
        return Mono.fromFuture(productDynamoDbAsyncTable.putItem(product));
    }

    public Flux<Product> getAll() {
        return Flux.from(productDynamoDbAsyncTable.scan().items());
    }
}
