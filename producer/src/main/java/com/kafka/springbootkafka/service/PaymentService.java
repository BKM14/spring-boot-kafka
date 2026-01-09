package com.kafka.springbootkafka.service;

import com.kafka.springbootkafka.dto.PaymentDto;
import com.kafka.springbootkafka.model.Approval;
import com.kafka.springbootkafka.model.FinalMessage;
import com.kafka.springbootkafka.model.Order;
import com.kafka.springbootkafka.model.Payment;
import com.kafka.springbootkafka.repository.ApprovalRepository;
import com.kafka.springbootkafka.repository.OrderRepository;
import com.kafka.springbootkafka.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ApprovalRepository approvalRepository;
    private final OrderRepository orderRepository;
    private final KafkaMessagePublisher kafkaMessagePublisher;

    public boolean doesApprovalIdExits(UUID id) {
        return paymentRepository.existsByApproveId(id);
    }

    public boolean doesPaymentIdExits(UUID id) {
        return paymentRepository.existsById(id);
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment getPaymentDetail(UUID id) {
        return paymentRepository.getPaymentsById(id);
    }

    public void processMessage(PaymentDto paymentDto, Payment savedPayment) {
        Approval approval = approvalRepository.findById(paymentDto.getApproveId())
                .orElseThrow(() -> new RuntimeException("Approval ID not found"));;
        Order order = orderRepository.findById(approval.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Order ID not found"));;

        approval.setOrder(null);
        FinalMessage messgae = FinalMessage.builder()
                .payment(savedPayment)
                .approval(approval)
                .order(order)
                .build();

        kafkaMessagePublisher.sendOrderEventsToTopic(messgae);
    }
}
