package com.elias.michalczuk.dynamodbspring.purchase.application;

import com.elias.michalczuk.dynamodbspring.DynamodbSpringApplication;
import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import com.elias.michalczuk.dynamodbspring.product.exception.ProductNotFoundException;
import com.elias.michalczuk.dynamodbspring.product.repository.ProductRepository;
import com.elias.michalczuk.dynamodbspring.purchase.api.dto.CreatePurchaseDto;
import com.elias.michalczuk.dynamodbspring.purchase.domain.Purchase;
import com.elias.michalczuk.dynamodbspring.purchase.repository.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PurchaseApplicationService {

    private PurchaseRepository purchaseRepository;
    private ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(DynamodbSpringApplication.class);

    public Mono<Purchase> create(CreatePurchaseDto purchase) {
        return Flux.fromIterable(purchase.getProductIds())
                .flatMap(productRepository::findById)
                .doOnError(err -> {
                    err.printStackTrace();
                    throw new ProductNotFoundException();
                })
                .parallel()
                .sequential()
                .collectList()
                .flatMap(products -> {
                    return purchaseRepository.save(
                            Purchase.builder()
                                    .products(products.stream().map(Product::getId)
                                    .collect(Collectors.toList()))
                                    .id(UUID.randomUUID()).build()
                    );
                })
                .doOnError(err -> err.printStackTrace());
    }
}
