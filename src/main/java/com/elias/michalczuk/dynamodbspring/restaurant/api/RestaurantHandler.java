package com.elias.michalczuk.dynamodbspring.restaurant.api;

import com.elias.michalczuk.dynamodbspring.restaurant.api.dtos.CreateRestaurantDto;
import com.elias.michalczuk.dynamodbspring.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RestaurantHandler {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CreateRestaurantDto.class)
                .flatMap(rest -> restaurantRepository.save(rest.toRestaurant()))
                .flatMap(rest -> ServerResponse.ok().build())
                .doOnError(err -> {
                    err.printStackTrace();
                });
    }

}
