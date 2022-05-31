package com.elias.michalczuk.dynamodbspring.product.api;

import com.elias.michalczuk.dynamodbspring.DynamodbSpringApplication;
import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import com.elias.michalczuk.dynamodbspring.product.repository.ProductRepository;
import com.elias.michalczuk.dynamodbspring.product.repository.RedisProduct;
import com.elias.michalczuk.dynamodbspring.product.repository.RedisProductRepository;
import com.elias.michalczuk.dynamodbspring.redis.Redis;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Component
public class ProductHandler {


    private static final Logger log = LoggerFactory.getLogger(DynamodbSpringApplication.class);

//    @Value("${spring.profiles.active}")
//    private String activeProfile;
//        log.info("active profile " + activeProfile);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private org.springframework.data.redis.core.ReactiveValueOperations<String, RedisProduct> reactiveValueOperations;

    public Mono<ServerResponse> create(ServerRequest request) {

        return request.bodyToMono(Product.class)
                .flatMap(product -> productRepository.save(product.generateId()))
                .doOnNext(this::saveRedis)
                .flatMap(product -> ServerResponse.ok().build())
                .doOnError(Throwable::printStackTrace);
    }

    private Mono<Boolean> saveRedis(Product product) {
        return reactiveValueOperations.set(product.getId().toString(), RedisProduct.fromProduct(product))
                .doOnError(e -> log.error("Error saving redis", e))
                .doOnNext(p -> log.info("product saved redis "));
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(productRepository.findAll(), Product.class).doOnError(err -> {
                    err.printStackTrace();
                });
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(reactiveValueOperations.get(id.toString())
                        .map(p -> p.toProduct())
                        .doOnNext(p -> log.info("Found product redis ", p.getId()))
                                .switchIfEmpty(productRepository.findById(id))
                        , Product.class).doOnError(err -> {
                    err.printStackTrace();
                });
    }
}
