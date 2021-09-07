package com.elias.michalczuk.dynamodbspring.customer.api;

import com.elias.michalczuk.dynamodbspring.customer.domain.Customer;
import com.elias.michalczuk.dynamodbspring.customer.repository.CustomerRepository;
import com.elias.michalczuk.dynamodbspring.restaurant.api.dtos.CreateRestaurantDto;
import com.elias.michalczuk.dynamodbspring.restaurant.domain.Restaurant;
import com.elias.michalczuk.dynamodbspring.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {

    @Autowired
    private CustomerRepository customerRepository;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Customer.class)
                .flatMap(customer -> customerRepository.save(customer.generateId()))
                .flatMap(customer -> ServerResponse.ok().build())
                .doOnError(err -> {
                    err.printStackTrace();
                });
    }
    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().body(customerRepository.findAll(), Customer.class);
    }
}
