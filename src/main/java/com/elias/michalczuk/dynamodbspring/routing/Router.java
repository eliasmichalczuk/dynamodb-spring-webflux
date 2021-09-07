package com.elias.michalczuk.dynamodbspring.routing;

import com.elias.michalczuk.dynamodbspring.customer.api.CustomerHandler;
import com.elias.michalczuk.dynamodbspring.product.api.ProductHandler;
import com.elias.michalczuk.dynamodbspring.purchase.api.PurchaseHandler;
import com.elias.michalczuk.dynamodbspring.restaurant.api.RestaurantHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class Router {
    @Bean
    public RouterFunction<ServerResponse> route(ProductHandler handler,
                                                PurchaseHandler purchaseHandler,
    CustomerHandler custHandler,
    RestaurantHandler restHandler) {

        return RouterFunctions
                .route(POST("/product/save").and(accept(MediaType.APPLICATION_JSON)), handler::create)
                .andRoute(GET("/product/getAll").and(accept(MediaType.APPLICATION_JSON)), handler::getAll)
                .andRoute(GET("/product/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::getById)
                .andRoute(POST("/purchase/create").and(accept(MediaType.APPLICATION_JSON)), purchaseHandler::create)
                .andRoute(GET("/purchase/getAll").and(accept(MediaType.APPLICATION_JSON)), purchaseHandler::getAll)
                .andRoute(POST("/restaurant").and(accept(MediaType.APPLICATION_JSON)), restHandler::create)
                .andRoute(GET("/restaurant/findNear").and(accept(MediaType.APPLICATION_JSON)), restHandler::findNear)
                .andRoute(POST("/customer").and(accept(MediaType.APPLICATION_JSON)), custHandler::create)
                .andRoute(GET("/customer").and(accept(MediaType.APPLICATION_JSON)), custHandler::getAll);
    }
}
