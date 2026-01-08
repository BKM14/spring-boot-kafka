package com.kafka.springbootkafka.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderDto {
    private BigDecimal amount;
}
