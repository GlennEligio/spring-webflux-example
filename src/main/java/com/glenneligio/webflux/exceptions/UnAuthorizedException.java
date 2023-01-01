package com.glenneligio.webflux.exceptions;

public class UnAuthorizedException extends RuntimeException{
    private String message;

    public UnAuthorizedException(String message) {
        super(message);
        this.message = message;
    }
    public UnAuthorizedException() {
    }
}
