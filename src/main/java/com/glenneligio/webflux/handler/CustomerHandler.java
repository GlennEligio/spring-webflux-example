package com.glenneligio.webflux.handler;

import com.glenneligio.webflux.dao.CustomerDao;
import com.glenneligio.webflux.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDao dao;

    public Mono<ServerResponse> loadCustomer(ServerRequest request) {
        Flux<Customer> customerFlux = dao.getCustomerList();
        return ServerResponse.ok().body(customerFlux, Customer.class);
    }

    public Mono<ServerResponse> findCustomer(ServerRequest request) {
        Integer id = Integer.parseInt(request.pathVariable("id"));
        Mono<Customer> customerMono = dao.getCustomerList().filter(c -> c.getId() == id).next();
        return ServerResponse.ok().body(customerMono, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<String> transformedCustomer = customerMono.map(c -> c.getId() + ":" + c.getName());
        return ServerResponse.ok().body(transformedCustomer, String.class);
    }
}
