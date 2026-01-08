package com.kafka.springbootkafka.controller;

import com.kafka.springbootkafka.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private  OrderService orderService;

    public void OrderController(OrderService orderService) {
        this.orderService = orderService;
    }






}
