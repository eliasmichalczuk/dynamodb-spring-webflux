package com.elias.michalczuk.dynamodbspring.product.api;

import com.elias.michalczuk.dynamodbspring.DynamodbSpringApplication;
import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import com.elias.michalczuk.dynamodbspring.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler {

    private static final Logger log = LoggerFactory.getLogger(DynamodbSpringApplication.class);

//    @Value("${spring.profiles.active}")
//    private String activeProfile;
//        log.info("active profile " + activeProfile);

    @Autowired
    private ProductRepository productRepository;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(product -> productRepository.save(product.generateId()))
                .flatMap(product -> ServerResponse.ok().build())
                .doOnError(err -> {
                    err.printStackTrace();
                });
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(productRepository.findAll(), Product.class).doOnError(err -> {
                    err.printStackTrace();
                });
    }
}
