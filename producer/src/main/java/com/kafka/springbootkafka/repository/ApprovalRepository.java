package com.kafka.springbootkafka.repository;

import com.kafka.springbootkafka.model.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, UUID> {
    boolean existsByOrder_Id(UUID orderId);
    Approval getApprovalById(UUID id);
}
