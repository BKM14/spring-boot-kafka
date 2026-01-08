package com.kafka.springbootkafka.service;

import com.kafka.springbootkafka.enums.EventType;
import com.kafka.springbootkafka.exception.InvalidStateTransitionException;
import com.kafka.springbootkafka.exception.OrderProjectionNotFoundException;
import com.kafka.springbootkafka.model.OrderProjection;
import com.kafka.springbootkafka.repository.OrderProjectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderProjectionService {

    private final OrderProjectionRepository repository;
    private final Map<EventType, Set<EventType>> invalidTransitions
            = Map.of(
                EventType.NONE, Set.of(EventType.ORDER_APPROVED, EventType.ORDER_REJECTED, EventType.ORDER_CREATED, EventType.PAYMENT_COMPLETE, EventType.PAYMENT_FAILED),
                EventType.ORDER_CREATED, Set.of(EventType.NONE, EventType.PAYMENT_COMPLETE, EventType.PAYMENT_FAILED),
                EventType.ORDER_APPROVED, Set.of(EventType.NONE, EventType.ORDER_CREATED),
                EventType.ORDER_REJECTED, Set.of(EventType.NONE, EventType.ORDER_CREATED),
                EventType.PAYMENT_COMPLETE, Set.of(EventType.NONE, EventType.ORDER_CREATED, EventType.ORDER_APPROVED, EventType.ORDER_REJECTED),
                EventType.PAYMENT_FAILED, Set.of(EventType.NONE, EventType.ORDER_CREATED, EventType.ORDER_APPROVED, EventType.ORDER_REJECTED)
            );

    public OrderProjection createOrderProjection(OrderProjection orderProjection) {
        orderProjection.setLastEventType(EventType.NONE);
        return repository.save(orderProjection);
    }

    public void approveOrder(UUID orderId) {
        OrderProjection projection = getValidOrderProjectionOrThrow(orderId, EventType.ORDER_APPROVED);
        projection.setApprovedAt(LocalDateTime.now());
        projection.setLastEventType(EventType.ORDER_APPROVED);
        repository.save(projection);
    }

    public void rejectOrder(UUID orderId) {
        OrderProjection projection = getValidOrderProjectionOrThrow(orderId, EventType.ORDER_REJECTED);
        projection.setLastEventType(EventType.ORDER_REJECTED);
        projection.setApprovedAt(LocalDateTime.now());
        repository.save(projection);
    }

    public void paySuccessForOrder(UUID orderId) {
        OrderProjection projection = getValidOrderProjectionOrThrow(orderId, EventType.PAYMENT_COMPLETE);
        projection.setPaidAt(LocalDateTime.now());
        projection.setLastEventType(EventType.PAYMENT_COMPLETE);
        repository.save(projection);
    }

    public void payFailedForOrder(UUID orderId) {
        OrderProjection projection = getValidOrderProjectionOrThrow(orderId, EventType.PAYMENT_FAILED);
        isValidTransitionOrThrow(projection.getLastEventType(), EventType.PAYMENT_FAILED);
        projection.setLastEventType(EventType.PAYMENT_FAILED);
        repository.save(projection);
    }

    private void isValidTransitionOrThrow(EventType prev, EventType next) {
        if (prev == next || !invalidTransitions.get(prev).contains(next)) {
            throw new InvalidStateTransitionException(prev, next);
        }
    }

    private OrderProjection getOrderProjectionOrThrow(UUID id) {
        return repository.findById(id).orElseThrow(() -> new OrderProjectionNotFoundException(id));
    }

    private OrderProjection getValidOrderProjectionOrThrow(UUID orderId, EventType next) {
        OrderProjection projection = getOrderProjectionOrThrow(orderId);
        isValidTransitionOrThrow(projection.getLastEventType(), next);
        return projection;
    }
}
