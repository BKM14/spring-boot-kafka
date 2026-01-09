package com.kafka.springbootkafka.controller;

import com.kafka.springbootkafka.dto.ApprovalDto;
import com.kafka.springbootkafka.dto.OrderDto;
import com.kafka.springbootkafka.model.Approval;
import com.kafka.springbootkafka.model.ApprovalStatus;
import com.kafka.springbootkafka.model.Order;
import com.kafka.springbootkafka.model.OrderStatus;
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
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ApprovalService approvalService;

    @PostMapping("/newOrder")
    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) {
        Order order = new Order();
        order.setAmount(orderDto.getAmount());
        order.setOrderStatus(OrderStatus.CREATED);

        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("orderId: " + savedOrder.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderDetails(@PathVariable UUID id) {
        if(!orderService.doesIdExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order id does not exists");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String result =  objectMapper.writeValueAsString(orderService.getOrderDetailById(id));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }












}
