package com.kafka.springbootkafka.service;

import com.kafka.springbootkafka.model.FinalMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class KafkaMessagePublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendOrderEventsToTopic(FinalMessage message) {
        try {
            kafkaTemplate.send("order_lifecycle_v1", objectMapper.writeValueAsString(message));
            System.out.println("Message sent to topic: " + message);
        } catch (Exception e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }
}