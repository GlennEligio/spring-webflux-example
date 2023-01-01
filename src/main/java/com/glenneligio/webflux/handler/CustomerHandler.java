package com.glenneligio.webflux.handler;

import com.glenneligio.webflux.dao.CustomerDao;
import com.glenneligio.webflux.dto.Customer;
import com.glenneligio.webflux.exceptions.ApiException;
import com.glenneligio.webflux.exceptions.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDao dao;

    public Mono<ServerResponse> loadCustomers(ServerRequest request) {
        Flux<Customer> customerFlux = dao.getCustomerList();
        return ServerResponse.ok().body(customerFlux, Customer.class);
    }

    public Mono<ServerResponse> findCustomer(ServerRequest request) {
//        Integer id = Integer.parseInt(request.pathVariable("id"));
//        Mono<Customer> customerMono = dao.getCustomerList().filter(c -> c.getId() == id).next();
//        return ServerResponse.ok().body(customerMono, Customer.class);
        return Mono.error(new ApiException(HttpStatus.NOT_FOUND, "You cant go to this route"));
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<Customer> transformedCustomer = customerMono.map(customer -> {
            customer.setId(new Random().nextInt()%100);
            return customer;
        });
        return ServerResponse.status(201).body(transformedCustomer, Customer.class);
    }
}
