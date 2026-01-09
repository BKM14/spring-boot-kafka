package com.kafka.springbootkafka.service;

import com.kafka.springbootkafka.model.Approval;
import com.kafka.springbootkafka.model.Order;
import com.kafka.springbootkafka.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public boolean doesIdExists(UUID id) {
        return orderRepository.existsById(id);
    }

    public Order getOrderDetailById(UUID id) {
        return orderRepository.getOrdersById(id);
    }


}
