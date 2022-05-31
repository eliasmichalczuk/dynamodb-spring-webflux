package com.elias.michalczuk.dynamodbspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class BufferWindowTesting {
    static Logger log = LoggerFactory.getLogger(BufferWindowTesting.class);
    public static void main(String[] args) throws InterruptedException {
        Flux.range(1,10)
        .buffer(3)
        .parallel()
        .runOn(Schedulers.parallel())
        .doOnNext(e -> log.info(e  + ", Thread id: " + Thread.currentThread().getId()))
        .subscribe();

        Flux.range(1,10)
                .window(3)
                .doOnNext(w -> log.info("window " + w.collectList()  + ", Thread id: " + Thread.currentThread().getId()))
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(e -> {
                    return e.doOnNext(a -> log.info(a  + ", Thread id: " + Thread.currentThread().getId()));
                });
                //.subscribe();

        ReactorTesting.tJoin();
    }
}
