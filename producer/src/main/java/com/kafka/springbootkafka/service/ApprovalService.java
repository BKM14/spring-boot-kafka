package com.kafka.springbootkafka.service;

import com.kafka.springbootkafka.model.Approval;
import com.kafka.springbootkafka.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApprovalService {
    public final ApprovalRepository approvalRepository;

    public Approval saveApproval(Approval approval) {
        return approvalRepository.save(approval);
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
