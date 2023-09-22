package com.vanguard.weather.exception;

public class InvalidAPIKeyException extends RuntimeException {

    public InvalidAPIKeyException(String message) {
        super(message);
    }
}
