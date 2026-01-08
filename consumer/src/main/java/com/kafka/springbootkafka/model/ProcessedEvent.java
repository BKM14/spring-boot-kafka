package com.kafka.springbootkafka.model;

import com.kafka.springbootkafka.enums.EventType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "processed_events", uniqueConstraints = @UniqueConstraint(columnNames = {
        "order_id",
        "event_type"
}))
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProcessedEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @CreationTimestamp
    @Column(name = "processed_at")
    private LocalDateTime processedAt;

}
