package com.glenneligio.webflux.dao;

import com.glenneligio.webflux.dto.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class CustomerDao {

    public static void sleepExecution(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Customer> getCustomers() {
        return IntStream.rangeClosed(1, 50)
                .peek(CustomerDao::sleepExecution)
                .peek(i -> System.out.println("Processing count: " + i))
                .mapToObj(i -> new Customer(i, "Customer" + i))
                .toList();
    }

    public Flux<Customer> getCustomersStream() {
        return Flux.range(1, 50)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("Processing count: " + i))
                .log()
                .map(i -> new Customer(i, "Customer" + i));
    }
}
