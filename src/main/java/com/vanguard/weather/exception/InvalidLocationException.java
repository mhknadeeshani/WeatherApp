package com.vanguard.weather.exception;

public class InvalidLocationException extends RuntimeException {

    public InvalidLocationException(String message) {
        super(message);
    }
}
