package com.kafka.springbootkafka.exception;

public class RetryableException extends RuntimeException {
    public RetryableException(String message) {
        super(message);
    }
}
