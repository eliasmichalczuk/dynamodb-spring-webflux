package com.elias.michalczuk.dynamodbspring.purchase.application;

import com.elias.michalczuk.dynamodbspring.DynamodbSpringApplication;
import com.elias.michalczuk.dynamodbspring.customer.domain.Customer;
import com.elias.michalczuk.dynamodbspring.customer.domain.exception.CustomerNotFoundException;
import com.elias.michalczuk.dynamodbspring.customer.repository.CustomerRepository;
import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import com.elias.michalczuk.dynamodbspring.product.exception.ProductNotFoundException;
import com.elias.michalczuk.dynamodbspring.product.repository.ProductRepository;
import com.elias.michalczuk.dynamodbspring.purchase.api.dto.CreatePurchaseDto;
import com.elias.michalczuk.dynamodbspring.purchase.api.dto.PurchaseGetAllDto;
import com.elias.michalczuk.dynamodbspring.purchase.domain.Purchase;
import com.elias.michalczuk.dynamodbspring.purchase.repository.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PurchaseApplicationService {

    private PurchaseRepository purchaseRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(DynamodbSpringApplication.class);

    public Mono<Purchase> create(CreatePurchaseDto purchase) {
        var customer = customerRepository.findById(purchase.getCustomerId())
                .switchIfEmpty(Mono.defer(() -> {
                    throw new CustomerNotFoundException();
                }))
                .onErrorStop();
        var products = Flux.fromIterable(purchase.getProductIds())
                .flatMap(uuid -> productRepository.findById(uuid)
                        .switchIfEmpty(Mono.defer(() -> {
                            throw new ProductNotFoundException();
                        }))
                        .onErrorStop()
                )
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .sequential()
                .collectList()
                .doOnError(err -> err.printStackTrace());
        return Mono.zip(products, customer).flatMap(result ->
                purchaseRepository.save(
                                Purchase.builder()
                                        .customerId(result.getT2().getId())
                                        .products(result.getT1().stream().map(Product::getId).collect(Collectors.toList()))
                                        .id(UUID.randomUUID())
                                        .build()
                        )
                        .doOnError(err -> err.printStackTrace())
                );
    }

    public Flux<PurchaseGetAllDto> getAll(LocalDateTime dataDe, LocalDateTime dataAte) {
        return findAll(dataDe, dataAte)
                .flatMap(purchase -> {
                    var customerMono = purchase.getCustomerId() != null ?
                            (customerRepository.findById(purchase.getCustomerId())
                            .switchIfEmpty(Mono.defer(() -> {
                                throw new ProductNotFoundException();
                            }))
                            .onErrorStop()) : Mono.just(Customer.builder().build());
                    var productsFlux = productRepository.findAllById(purchase.getProducts())
                            .parallel()
                            .runOn(Schedulers.boundedElastic())
                            .sequential()
                            .collectList();
                    return Mono.zip(customerMono, productsFlux).map(result -> PurchaseGetAllDto.of(result.getT2(), result.getT1()));
                });
    }

    public Flux<Purchase> findAll(LocalDateTime dataDe, LocalDateTime dataAte) {
        return dataDe != null && dataAte != null ?
                purchaseRepository.findByDateBetween(dataDe, dataAte) : purchaseRepository.findAll();
    }
}
