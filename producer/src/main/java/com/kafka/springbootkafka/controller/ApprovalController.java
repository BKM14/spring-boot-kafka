package com.kafka.springbootkafka.controller;

import com.kafka.springbootkafka.dto.ApprovalDto;
import com.kafka.springbootkafka.model.Approval;
import com.kafka.springbootkafka.model.ApprovalStatus;
import com.kafka.springbootkafka.model.Order;
import com.kafka.springbootkafka.service.ApprovalService;
import com.kafka.springbootkafka.service.OrderService;
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
@RequestMapping("/api/approval")
@RequiredArgsConstructor
public class ApprovalController {
    private final OrderService orderService;
    private final ApprovalService approvalService;

    @PostMapping
    public ResponseEntity<String> makeApproval(@RequestBody ApprovalDto approvalDto) {
        if(!orderService.doesIdExists(approvalDto.getOrderId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order is not created");
        }

        if(approvalService.doesOrderIdExists(approvalDto.getOrderId())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Already processed");
        }

        Approval approval = new Approval();
        Order order = new Order();
        order.setId(approvalDto.getOrderId());
        approval.setOrder(order);
        if(approvalDto.isApprove())  {
            approval.setStatus(ApprovalStatus.APPROVED);
        } else {
            approval.setStatus(ApprovalStatus.REJECTED);
        }

        Approval savedApproval = approvalService.saveApproval(approval);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("approvalId: " + savedApproval.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getApprovalDetails(@PathVariable UUID id) {
        if(!approvalService.doesApprovalIdExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Approval Id does not exits");
        }
        Approval approval = approvalService.getApprovalDetailById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(approval);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }
}
