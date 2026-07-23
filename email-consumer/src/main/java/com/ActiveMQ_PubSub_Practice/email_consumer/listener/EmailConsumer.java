package com.ActiveMQ_PubSub_Practice.email_consumer.listener;

import com.ActiveMQ_PubSub_Practice.email_consumer.dto.OrderEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final ObjectMapper objectMapper;

    public EmailConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${app.jms.topic}")
    public void receive(String json) throws JsonProcessingException {

        OrderEvent event =
                objectMapper.readValue(json, OrderEvent.class);

        System.out.println();
        System.out.println("========== EMAIL SERVICE ==========");
        System.out.println("Sending confirmation email...");
        System.out.println(event);
        System.out.println("===================================");
        System.out.println();
    }
}
