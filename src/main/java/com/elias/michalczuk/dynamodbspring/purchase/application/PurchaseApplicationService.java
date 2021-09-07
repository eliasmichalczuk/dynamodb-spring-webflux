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
        return Flux.fromIterable(purchase.getProductIds())
                .flatMap(uuid -> productRepository.findById(uuid)
                        .switchIfEmpty(Mono.defer(() -> {
                            throw new ProductNotFoundException();
                        }))
                        .onErrorStop()
                )
                .zipWith(customer)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .sequential()
                .collectList()
                .flatMap(zipProdCustomer ->
                    purchaseRepository.save(
                            Purchase.builder()
                                    .customerId(zipProdCustomer.get(0).getT2().getId())
                                    .products(zipProdCustomer.stream().map(p-> p.getT1().getId()).collect(Collectors.toList()))
                                    .id(UUID.randomUUID()).build()
                    )
                )
                .doOnError(err -> err.printStackTrace());
    }

    public Flux<PurchaseGetAllDto> getAll(LocalDateTime dataDe, LocalDateTime dataAte) {
        return findAll(dataDe, dataAte)
                .flatMap(purchase -> {
                    var customer = purchase.getCustomerId() != null ?
                            (customerRepository.findById(purchase.getCustomerId())
                            .switchIfEmpty(Mono.defer(() -> {
                                throw new ProductNotFoundException();
                            }))
                            .onErrorStop()) : Mono.just(Customer.builder().build());
                   return productRepository.findAllById(purchase.getProducts())
                           .zipWith(customer)
                            .parallel()
                            .runOn(Schedulers.boundedElastic())
                            .sequential()
                            .collectList()
                            .map(products -> PurchaseGetAllDto.of(products.stream().map(p -> p.getT1()).collect(Collectors.toList()), products.get(0).getT2()));
                });
    }

    public Flux<Purchase> findAll(LocalDateTime dataDe, LocalDateTime dataAte) {
        return dataDe != null && dataAte != null ?
                purchaseRepository.findByDateBetween(dataDe, dataAte) : purchaseRepository.findAll();
    }
}
