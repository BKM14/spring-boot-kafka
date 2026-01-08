package com.kafka.springbootkafka.controller;

import com.kafka.springbootkafka.dto.ApprovalDto;
import com.kafka.springbootkafka.dto.OrderDto;
import com.kafka.springbootkafka.model.Approval;
import com.kafka.springbootkafka.model.ApprovalStatus;
import com.kafka.springbootkafka.model.Order;
import com.kafka.springbootkafka.model.OrderStatus;
import com.kafka.springbootkafka.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Getter
@Setter
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/newOrder")
    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) {
        Order order = new Order();
        order.setAmount(orderDto.getAmount());
        order.setOrderStatus(OrderStatus.CREATED);

        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("orderId: " + savedOrder.getId());
    }

    @PutMapping("/approval")
    public ResponseEntity<String> makeApproval(@PathVariable UUID id, @RequestBody ApprovalDto approvalDto) {
        if(!orderService.doesIdExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order is not created");
        }

        Approval approval = new Approval();
        if(approvalDto.isApprove())  {
            approval.setStatus(ApprovalStatus.APPROVED);
        } else {
            approval.setStatus(ApprovalStatus.REJECTED);
        }

        Approval savedApproval = orderService.
    }










}
