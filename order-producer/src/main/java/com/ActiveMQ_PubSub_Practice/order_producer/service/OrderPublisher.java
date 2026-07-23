package com.ActiveMQ_PubSub_Practice.order_producer.service;

import com.ActiveMQ_PubSub_Practice.order_producer.dto.OrderEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPublisher {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.jms.topic}")
    private String topic;

    public void publish(OrderEvent orderEvent) throws JsonProcessingException {

        String json = objectMapper.writeValueAsString(orderEvent);

        jmsTemplate.convertAndSend(topic, json);

        System.out.println("--------------------------------");
        System.out.println("Publishing Order Event");
        System.out.println(json);
        System.out.println("--------------------------------");
    }
}