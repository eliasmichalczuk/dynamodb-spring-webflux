package com.elias.michalczuk.dynamodbspring.purchase.api;

import com.elias.michalczuk.dynamodbspring.DynamodbSpringApplication;
import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import com.elias.michalczuk.dynamodbspring.product.repository.ProductRepository;
import com.elias.michalczuk.dynamodbspring.purchase.api.dto.CreatePurchaseDto;
import com.elias.michalczuk.dynamodbspring.purchase.application.PurchaseApplicationService;
import com.elias.michalczuk.dynamodbspring.purchase.domain.Purchase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PurchaseHandler {

    private static final Logger log = LoggerFactory.getLogger(DynamodbSpringApplication.class);

    @Autowired
    private PurchaseApplicationService purchaseApplicationService;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CreatePurchaseDto.class)
                .flatMap(purchase -> purchaseApplicationService.create(purchase))
                .flatMap(purchase -> ServerResponse.ok().build())
                .doOnError(err -> {
                    err.printStackTrace();
                });
    }

//    public Mono<ServerResponse> getAll(ServerRequest request) {
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(productRepository.findAll(), Product.class).doOnError(err -> {
//                    err.printStackTrace();
//                });
//    }
}
