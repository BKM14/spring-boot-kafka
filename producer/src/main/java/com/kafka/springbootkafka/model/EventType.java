package com.kafka.springbootkafka.model;

public enum EventType {
    NONE,
    ORDER_CREATED,
    ORDER_APPROVED,
    ORDER_REJECTED,
    PAYMENT_COMPLETE,
    PAYMENT_FAILED
}


