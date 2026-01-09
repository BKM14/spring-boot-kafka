package com.kafka.springbootkafka.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PaymentDto {
    private UUID approveId;
    private boolean payment;
}
