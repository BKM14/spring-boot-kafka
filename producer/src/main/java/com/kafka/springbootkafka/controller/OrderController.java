package com.kafka.springbootkafka.controller;

import com.kafka.springbootkafka.dto.OrderDto;
import com.kafka.springbootkafka.model.Order;
import com.kafka.springbootkafka.model.OrderStatus;
import com.kafka.springbootkafka.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("order created");
    }








}
