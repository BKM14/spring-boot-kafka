package com.kafka.springbootkafka.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class ApprovalDto {
    private UUID orderId;
    private boolean approve;

}
