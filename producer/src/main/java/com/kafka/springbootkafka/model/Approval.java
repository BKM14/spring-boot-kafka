package com.kafka.springbootkafka.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "approvals")
@Getter
@Setter
public class Approval {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", unique = true, nullable = false)
    private Order order;

    //private UUID orderId;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
