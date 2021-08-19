package com.elias.michalczuk.dynamodbspring.purchase.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

//@Configuration
//public class PurchaseRouter {
//    @Bean
//    public RouterFunction<ServerResponse> route(PurchaseHandler handler) {
//
//        return RouterFunctions
//                .route(POST("/purchase/create").and(accept(MediaType.APPLICATION_JSON)), handler::create)
//                .andRoute(GET("/purchase/getAll").and(accept(MediaType.APPLICATION_JSON)), handler::create);
//    }
//}
