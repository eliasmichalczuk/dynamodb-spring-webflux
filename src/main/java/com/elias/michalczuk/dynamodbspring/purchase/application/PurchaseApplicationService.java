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
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
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
                .flatMap(uuid -> productRepository.findById(uuid)
                        .switchIfEmpty(Mono.just(new Product()))
                        .map(product -> {
                            if (product.getId() == null) {
                                throw new ProductNotFoundException();
                            }
                            return product;
                        })
                        .onErrorStop()
                )
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .sequential()
                .collectList()
                .flatMap(products ->
                    purchaseRepository.save(
                            Purchase.builder()
                                    .products(products)
                                    .id(UUID.randomUUID()).build()
                    )
                )
                .doOnError(err -> err.printStackTrace());
    }
}
