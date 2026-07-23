package com.ActiveMQ_PubSub_Practice.inventory_consumer.listener;

import com.ActiveMQ_PubSub_Practice.inventory_consumer.dto.OrderEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryConsumer {

    private final ObjectMapper objectMapper;

    public InventoryConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${app.jms.topic}")
    public void receive(String json) throws JsonProcessingException {

        OrderEvent event =
                objectMapper.readValue(json, OrderEvent.class);

        System.out.println();
        System.out.println("======= INVENTORY SERVICE =======");
        System.out.println("Updating inventory...");
        System.out.println(event);
        System.out.println("=================================");
        System.out.println();
    }
}