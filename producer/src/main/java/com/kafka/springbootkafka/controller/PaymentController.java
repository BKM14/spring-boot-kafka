package com.kafka.springbootkafka.controller;

import com.kafka.springbootkafka.dto.PaymentDto;
import com.kafka.springbootkafka.model.Payment;
import com.kafka.springbootkafka.model.PaymentStatus;
import com.kafka.springbootkafka.service.ApprovalService;
import com.kafka.springbootkafka.service.OrderService;
import com.kafka.springbootkafka.service.PaymentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Getter
@Setter
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final ApprovalService approvalService;
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> makePayment(@RequestBody PaymentDto paymentDto) {
        if(!approvalService.doesApprovalIdExists(paymentDto.getApproveId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot do the payment");
        }

        if(paymentService.doesApprovalIdExits(paymentDto.getApproveId())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Already processed");
        }

        Payment payment = new Payment();
        payment.setApproveId(paymentDto.getApproveId());
        if(paymentDto.isPayment()) {
            payment.setStatus(PaymentStatus.COMPLETED);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        Payment savedPayment = paymentService.savePayment(payment);
        paymentService.processMessage(paymentDto, savedPayment);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("paymentId: " + savedPayment.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getPaymentDetail(@PathVariable UUID id) {
        if(!paymentService.doesPaymentIdExits(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment Id does not exits");
        }

        Payment payment = paymentService.getPaymentDetail(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(payment);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }
}
