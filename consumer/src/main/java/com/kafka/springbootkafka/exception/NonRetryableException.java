package com.kafka.springbootkafka.exception;

public class NonRetryableException extends RuntimeException {
    public NonRetryableException(String message) {
        super(message);
    }
}
