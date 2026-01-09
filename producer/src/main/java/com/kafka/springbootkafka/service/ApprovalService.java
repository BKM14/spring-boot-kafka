package com.kafka.springbootkafka.service;

import com.kafka.springbootkafka.model.Approval;
import com.kafka.springbootkafka.model.ApprovalStatus;
import com.kafka.springbootkafka.model.EventEnvelope;
import com.kafka.springbootkafka.model.EventType;
import com.kafka.springbootkafka.producer.MessageProducer;
import com.kafka.springbootkafka.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApprovalService {
    public final ApprovalRepository approvalRepository;
    private final MessageProducer producer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Approval saveApproval(Approval approval) {
        Approval saved = approvalRepository.save(approval);

        EventEnvelope envelope = EventEnvelope.builder()
                .eventType(approval.getStatus().equals(ApprovalStatus.APPROVED)
                        ? EventType.ORDER_APPROVED : EventType.ORDER_REJECTED)
                .payload(saved.getOrder().getId().toString())
                .build();

        producer.sendMessage("order_lifecyle_v1", objectMapper.writeValueAsString(envelope));

        return saved;
    }

    public boolean doesOrderIdExists(UUID orderId) {
        return approvalRepository.existsByOrder_Id(orderId);
    }

    public boolean doesApprovalIdExists(UUID approvalId) {
        return approvalRepository.existsById(approvalId);
    }

    public Approval getApprovalDetailById(UUID id) {
        Approval approval  = approvalRepository.getApprovalById(id);
        approval.setOrder(null);
        return approval;
    }
}
