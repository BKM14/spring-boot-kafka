package com.kafka.springbootkafka.model;

import com.kafka.springbootkafka.enums.EventType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class EventEnvelope {

    UUID eventId;
    EventType eventType;
    LocalDateTime occurredAt;
    String payload;

}
