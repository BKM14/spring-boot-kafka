package com.kafka.springbootkafka.exception;

import java.util.UUID;

public class OrderProjectionNotFoundException extends RuntimeException{

    public OrderProjectionNotFoundException(UUID id) {
        super("OrderProjection: " + id + " not found.");
    }

}
