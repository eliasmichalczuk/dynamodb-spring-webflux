package com.elias.michalczuk.dynamodbspring;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelTesting {

    public static void main(String[] args) throws InterruptedException {
        Logger log = LoggerFactory.getLogger(ParallelTesting.class);
        val time = System.currentTimeMillis();

        Flux.fromIterable(IntStream.range(1, 3).boxed().collect(Collectors.toList()))
                .log()
                .parallel()
                .runOn(Schedulers.parallel())
                .doOnComplete(() -> {
                    //log.info("complete time: " + (System.currentTimeMillis() - time));
                    log.info("thread " + Thread.currentThread().getId());
                })
                //.flatMap(value ->
                    //Mono.just(value).subscribeOn(Schedulers.parallel()),2)
                .subscribe();


        ReactorTesting.tJoin();
    }
}
