package com.kafka.springbootkafka.service;

import com.kafka.springbootkafka.model.FinalMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessagePublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderEventsToTopic(FinalMessage message) {
        try {
            // "order-events" is the topic name. You can change this.
            kafkaTemplate.send("topic", message);
            System.out.println("Message sent to topic: " + message);
        } catch (Exception e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }
}