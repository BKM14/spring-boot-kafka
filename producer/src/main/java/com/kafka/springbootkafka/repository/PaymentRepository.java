package com.kafka.springbootkafka.repository;

import com.kafka.springbootkafka.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    boolean existsByApproveId(UUID approveId);
    Payment getPaymentsById(UUID id);

}
