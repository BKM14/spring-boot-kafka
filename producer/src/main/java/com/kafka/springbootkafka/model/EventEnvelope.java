package com.kafka.springbootkafka.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Builder
@Getter
public class EventEnvelope {

    UUID eventId;
    EventType eventType;
    LocalDateTime occurredAt;
    String payload;

}
