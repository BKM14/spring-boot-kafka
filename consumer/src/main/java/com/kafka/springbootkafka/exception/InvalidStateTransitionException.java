package com.kafka.springbootkafka.exception;

import com.kafka.springbootkafka.enums.EventType;

public class InvalidStateTransitionException extends NonRetryableException {
    public InvalidStateTransitionException(EventType prev, EventType cur) {
        super("Transition from " + prev + " to " + cur + " is not allowed.");
    }
}
