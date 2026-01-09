package com.kafka.springbootkafka.model;

import com.kafka.springbootkafka.enums.EventType;
import com.kafka.springbootkafka.enums.OrderStatus;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_projections")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderProjection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID orderId;

    @Nonnull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal amount;

    private LocalDateTime createdAt;

    private LocalDateTime approvedAt;

    private LocalDateTime paidAt;

    @Enumerated(EnumType.STRING)
    private EventType lastEventType;

}
