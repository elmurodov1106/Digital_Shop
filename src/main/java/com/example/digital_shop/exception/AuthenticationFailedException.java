package com.example.digital_shop.exception;

public class AuthenticationFailedException extends RuntimeException{
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
