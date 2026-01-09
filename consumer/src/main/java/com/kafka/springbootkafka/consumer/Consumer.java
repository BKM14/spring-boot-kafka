package com.kafka.springbootkafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.springbootkafka.enums.EventType;
import com.kafka.springbootkafka.exception.NonRetryableException;
import com.kafka.springbootkafka.model.ConsumedOrder;
import com.kafka.springbootkafka.model.EventEnvelope;
import com.kafka.springbootkafka.model.OrderProjection;
import com.kafka.springbootkafka.model.ProcessedEvent;
import com.kafka.springbootkafka.service.OrderProjectionService;
import com.kafka.springbootkafka.service.ProcessedEventService;
import com.kafka.springbootkafka.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log
public class Consumer {

    private final OrderProjectionService projectionService;
    private final ProcessedEventService processedEventService;
    private final ObjectMapper objectMapper;

    @RetryableTopic(
            backOff = @BackOff(delay = 1000, multiplier = 2, maxDelay = 5000),
            attempts = "5",
            exclude = {NonRetryableException.class, JsonProcessingException.class}
    )
    @KafkaListener(topics = "order_lifecyle_v1", groupId = "order_logging")
    public void consume(String data) {
        EventEnvelope envelope = objectMapper.readValue(data, EventEnvelope.class);
        String payload = envelope.getPayload();
        switch (envelope.getEventType()) {
            case ORDER_CREATED -> {
                ConsumedOrder order = objectMapper.readValue(payload, ConsumedOrder.class);
                OrderProjection projection = Mapper.consumedOrderToOrderProjection(order);
                if (checkIfAlreadyProcessed(order.getId(), envelope.getEventType())) return;
                projectionService.createOrderProjection(projection);
                ProcessedEvent processedEvent = ProcessedEvent.builder()
                        .id(order.getId())
                        .processedAt(LocalDateTime.now())
                        .eventType(envelope.getEventType())
                        .build();
                processedEventService.createEvent(processedEvent);
                log.info("Order projection created.");
            }
            case ORDER_APPROVED -> {
                UUID orderId = objectMapper.readValue(payload, UUID.class);
                if (checkIfAlreadyProcessed(orderId, envelope.getEventType())) return;
                projectionService.approveOrder(orderId);
                log.info("Order approved.");
            }
            case ORDER_REJECTED -> {
                UUID orderId = objectMapper.readValue(payload, UUID.class);
                if (checkIfAlreadyProcessed(orderId, envelope.getEventType())) return;
                projectionService.rejectOrder(orderId);
                log.info("Order rejected.");
            }
            case PAYMENT_COMPLETE -> {
                UUID orderId = objectMapper.readValue(payload, UUID.class);
                if (checkIfAlreadyProcessed(orderId, envelope.getEventType())) return;
                projectionService.paySuccessForOrder(orderId);
                log.info("Payment complete.");
            }
            case PAYMENT_FAILED -> {
                UUID orderId = objectMapper.readValue(payload, UUID.class);
                if (checkIfAlreadyProcessed(orderId, envelope.getEventType())) return;
                projectionService.payFailedForOrder(orderId);
                log.info("Payment failed.");
            }
            case NONE -> log.info("Nothing to process.");
            default -> log.info("Invalid event type.");
        }
    }

    private boolean checkIfAlreadyProcessed(UUID orderId, EventType eventType) {
        if (processedEventService.alreadyProcessed(orderId, eventType)) {
            log.info("Already processed EventType: " + eventType + " for orderId: " + orderId);
            return true;
        }
        return false;
    }

}
