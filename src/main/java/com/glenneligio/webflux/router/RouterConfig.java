package com.glenneligio.webflux.router;

import com.glenneligio.webflux.dto.Customer;
import com.glenneligio.webflux.handler.CustomerHandler;
import com.glenneligio.webflux.handler.CustomerStreamHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private CustomerHandler customerHandler;

    @Autowired
    private CustomerStreamHandler customerStreamHandler;


    @Bean
    @RouterOperations(value = {
            @RouterOperation(
                    path = "/router/customers",
                    beanClass = CustomerHandler.class,
                    produces = {
                            MediaType.APPLICATION_JSON_VALUE
                    },
                    beanMethod = "loadCustomers",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "loadCustomers",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful customer fetching",
                                            content = @Content(
                                                    array = @ArraySchema(
                                                            schema = @Schema(
                                                                    implementation = Customer.class
                                                            )
                                                    )
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/router/customers/{id}",
                    beanClass = CustomerHandler.class,
                    produces = {
                            MediaType.APPLICATION_JSON_VALUE
                    },
                    beanMethod = "findCustomer",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "findCustomer",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful found customer with specified id",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = Customer.class
                                                    )
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "No customer found using specified id"
                                    ),
                            },
                            parameters = {
                                    @Parameter(
                                            in = ParameterIn.PATH,
                                            name = "id"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/router/customers",
                    beanClass = CustomerHandler.class,
                    produces = {
                            MediaType.APPLICATION_JSON_VALUE
                    },
                    beanMethod = "saveCustomer",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "saveCustomer",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Successful saved customer",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = Customer.class
                                                    )
                                            )
                                    )
                            },
                            requestBody = @RequestBody(
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Customer.class
                                            )
                                    )
                            )
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/router/customers", customerHandler::loadCustomers)
                .GET("/router/customers/stream", customerStreamHandler::getCustomers)
                .GET("/router/customers/{id}", customerHandler::findCustomer)
                .POST("/router/customers", customerHandler::saveCustomer)
                .build();
    }
}
