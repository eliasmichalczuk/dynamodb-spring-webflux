package com.elias.michalczuk.dynamodbspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class ReactorTesting {

    public static void main(String[] args) throws InterruptedException {
        Logger log = LoggerFactory.getLogger(ReactorTesting.class);
        Hooks.onOperatorDebug();
        var mono1 = Mono.defer(() -> Mono.just(1))
                .subscribeOn(Schedulers.boundedElastic());
        var mono2 = Mono.defer(() -> Mono.just(2))
                .subscribeOn(Schedulers.boundedElastic());

        Mono.zip(mono1, mono2)
                .log();
//                .subscribe(t -> {
//                    System.out.println(" res : " + t.getT1() +" " + t.getT2());
//                });


        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5, 6, 7).delayElements(Duration.ofMillis(100));
//
//        Flux<Integer> share = integerFlux.share();
//
//        integerFlux.subscribe(e -> System.out.println("sub1 " + e));
//        Thread.sleep(500);
//        share.subscribe(e -> System.out.println("  sub2 " + e));

        integerFlux
                .subscribeOn(Schedulers.boundedElastic())
                .log()
                .doOnNext(e -> {
                    System.out.println("thread: "+ Thread.currentThread().getName());
                });
//                .subscribe(e -> System.out.println(e), (err) -> err.printStackTrace());

        Flux.interval(Duration.ofMillis(1))
                .log()
                .onBackpressureDrop()
                .concatMap(x -> Mono.delay(Duration.ofMillis(100)));
                //.blockLast();


        //System.out.println("\n******** Using concatMap() *********");
        Flux.range(1, 15)
                .delayElements(Duration.ofMillis(1200))
                .concatMap(item -> Flux.just(item).delayElements(Duration.ofMillis(1)))
                .timeout(Duration.ofMillis(1000));
                //.subscribe(x -> System.out.print(x + " "));

//flatMapMany x flatMap
        Mono.just(1)
                .flatMapMany(e -> Flux.just(e*2, e*3));
                //.subscribe(System.out::println);

        flatAndRetry(Flux.just(1,2,3,4,5,6,7));
               // .subscribe(System.out::println);


        Mono.just(1)
                .zipWith(Mono.just(2));
//                .subscribe(e -> {
//                    log.info(e.toString());
//                });


        String key = "message";
        Mono<String> r = Mono.just("Hello")
                .flatMap(s ->
                        Mono.deferContextual(ctx -> Mono.just(s + " " + ctx.get(key)))
                    )
                .contextWrite(ctx -> ctx.put(key, "World"));

        Flux<Integer> a = Flux.just(1,2,3)
                .flatMap(i -> {
                    log.info("--------" + i.toString());
                    return Flux.range(i,i*2);
                });
                a.subscribe(e->log.info(e.toString()));

        tJoin();
    }

    public static void tJoin() throws InterruptedException {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            t.join();
        }
    }

    static Flux<java.lang.Integer> flatAndRetry(Flux<java.lang.Integer> stream) {
        return stream.flatMap(e -> {
            if (e==4) throw new RuntimeException("4 aahh");
            return Mono.just(e);
        })
        .retry(2);
    }
}
