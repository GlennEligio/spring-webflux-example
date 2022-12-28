package com.glenneligio.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    @Test
    public void monoComplete() {
        Mono<?> monoComplete = Mono.just(1)
                .log();
        monoComplete.subscribe(System.out::println);
    }

    @Test
    public void monoWithError() {
        Mono<?> monoWithError = Mono.just(1)
                .then(Mono.error(new RuntimeException("Exception occurred")))
                .log();

        monoWithError.subscribe(System.out::println, System.out::println);
    }

    @Test
    public void fluxComplete() {
        Flux<?> fluxComplete = Flux.just(1,2,3,4)
                .log();
        fluxComplete.subscribe(System.out::println);
    }

    @Test
    public void fluxWithError() {
        Flux<?> fluxWithError = Flux.just(1,2,3,4)
                .concatWithValues(5,6,7)
                .concatWith(Flux.error(new RuntimeException("Some error occured")))
                .log();
        fluxWithError.subscribe(System.out::println, System.out::println);
    }
}
