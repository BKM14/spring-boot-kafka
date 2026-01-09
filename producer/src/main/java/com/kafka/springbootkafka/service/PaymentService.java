package com.kafka.springbootkafka.service;

import com.kafka.springbootkafka.model.*;
import com.kafka.springbootkafka.producer.MessageProducer;
import com.kafka.springbootkafka.repository.ApprovalRepository;
import com.kafka.springbootkafka.repository.OrderRepository;
import com.kafka.springbootkafka.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ApprovalRepository approvalRepository;
    private final OrderRepository orderRepository;
    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final MessageProducer messageProducer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean doesApprovalIdExits(UUID id) {
        return paymentRepository.existsByApproveId(id);
    }

    public boolean doesPaymentIdExits(UUID id) {
        return paymentRepository.existsById(id);
    }

    public Payment savePayment(Payment payment) {

        Payment saved =  paymentRepository.save(payment);
        EventEnvelope envelope = EventEnvelope.builder()
                .eventType(saved.getStatus().equals(PaymentStatus.COMPLETED) ? EventType.PAYMENT_COMPLETE : EventType.PAYMENT_FAILED)
                .payload(getOrderId(saved).toString())
                .build();

        messageProducer.sendMessage("order_lifecycle_v1", objectMapper.writeValueAsString(envelope));
        return saved;

    }

    public Payment getPaymentDetail(UUID id) {
        return paymentRepository.getPaymentsById(id);
    }

    public UUID getOrderId(Payment savedPayment) {
        Approval approval = approvalRepository.findById(savedPayment.getApproveId())
                .orElseThrow(() -> new RuntimeException("Approval ID not found"));;
        Order order = orderRepository.findById(approval.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Order ID not found"));;

       return order.getId();
    }
}
