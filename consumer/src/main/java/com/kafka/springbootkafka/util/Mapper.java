package com.kafka.springbootkafka.util;

import com.kafka.springbootkafka.enums.EventType;
import com.kafka.springbootkafka.model.ConsumedOrder;
import com.kafka.springbootkafka.model.OrderProjection;

public class Mapper {

    public static OrderProjection consumedOrderToOrderProjection(ConsumedOrder order) {
        return OrderProjection.builder()
                .orderId(order.getId())
                .status(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .lastEventType(EventType.NONE)
                .amount(order.getAmount())
                .build();
    }

}
