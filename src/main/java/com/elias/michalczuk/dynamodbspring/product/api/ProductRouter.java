package com.elias.michalczuk.dynamodbspring.product.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class ProductRouter {
    @Bean
    public RouterFunction<ServerResponse> route(ProductHandler handler) {

        return RouterFunctions
                .route(POST("/product/save").and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(GET("/product/getAll").and(accept(MediaType.APPLICATION_JSON)), handler::getAll);
    }
}
