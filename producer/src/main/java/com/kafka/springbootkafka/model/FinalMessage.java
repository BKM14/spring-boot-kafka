package com.kafka.springbootkafka.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinalMessage {
    private Order order;
    private Approval approval;
    private Payment payment;
}
