package com.kafka.springbootkafka.exception;

import java.util.UUID;

public class OrderProjectionNotFoundException extends NonRetryableException{

    public OrderProjectionNotFoundException(UUID id) {
        super("OrderProjection: " + id + " not found.");
    }

}
