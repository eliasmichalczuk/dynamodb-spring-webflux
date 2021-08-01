package com.elias.michalczuk.dynamodbspring.product.api;

import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import com.elias.michalczuk.dynamodbspring.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ProductHandler {

    private final ProductRepository productRepository;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(product -> productRepository.save(product.generateId()))
//                .flatMap(product -> ServerResponse.created(URI.create("/product/" + product.getId().tos)).build());
                .flatMap(product -> ServerResponse.ok().build());
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(productRepository.getAll(), Product.class);
    }
}
