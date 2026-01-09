package com.kafka.springbootkafka.service;

import com.kafka.springbootkafka.model.EventEnvelope;
import com.kafka.springbootkafka.model.EventType;
import com.kafka.springbootkafka.model.Order;
import com.kafka.springbootkafka.producer.MessageProducer;
import com.kafka.springbootkafka.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MessageProducer producer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Order saveOrder(Order order) {
        Order saved = orderRepository.save(order);
        EventEnvelope envelope = EventEnvelope.builder()
                .eventType(EventType.ORDER_CREATED)
                .payload(objectMapper.writeValueAsString(saved))
                .build();
        System.out.println(objectMapper.writeValueAsString(envelope));
        producer.sendMessage("order_lifecycle_v1", objectMapper.writeValueAsString(envelope));

        return saved;
    }

    public boolean doesIdExists(UUID id) {
        return orderRepository.existsById(id);
    }

    public Order getOrderDetailById(UUID id) {
        return orderRepository.getOrdersById(id);
    }


}
