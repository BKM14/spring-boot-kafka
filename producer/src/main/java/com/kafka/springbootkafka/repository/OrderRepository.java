package com.kafka.springbootkafka.repository;

import com.kafka.springbootkafka.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Order getOrdersById(UUID id);
}
