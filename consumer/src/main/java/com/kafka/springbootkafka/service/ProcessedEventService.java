package com.kafka.springbootkafka.service;

import com.kafka.springbootkafka.enums.EventType;
import com.kafka.springbootkafka.model.ProcessedEvent;
import com.kafka.springbootkafka.repository.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessedEventService {

    private final ProcessedEventRepository repository;

    public ProcessedEvent createEvent(ProcessedEvent processedEvent) {
        return repository.save(processedEvent);
    }

    public boolean alreadyProcessed(UUID orderId, EventType eventType) {
        return repository.existsByEventTypeAndOrderId(eventType, orderId);
    }

}
