package com.kafka.springbootkafka.service;

import com.kafka.springbootkafka.model.Order;
import com.kafka.springbootkafka.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
