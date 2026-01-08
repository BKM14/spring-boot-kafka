package com.kafka.springbootkafka.repository;

import com.kafka.springbootkafka.model.OrderProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderProjectionRepository extends JpaRepository<OrderProjection, UUID> {
}
