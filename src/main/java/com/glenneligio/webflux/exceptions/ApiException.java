package com.glenneligio.webflux.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@Data
@AllArgsConstructor
public class ApiException extends RuntimeException {

    private final HttpStatus code;
    private final String message;
}
