package com.kafka.springbootkafka.repository;

import com.kafka.springbootkafka.enums.EventType;
import com.kafka.springbootkafka.model.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, UUID> {

    boolean existsByEventTypeAndOrderId(EventType eventType, UUID orderId);

}
