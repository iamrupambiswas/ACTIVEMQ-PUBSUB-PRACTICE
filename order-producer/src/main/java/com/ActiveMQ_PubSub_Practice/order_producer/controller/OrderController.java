package com.ActiveMQ_PubSub_Practice.order_producer.controller;

import com.ActiveMQ_PubSub_Practice.order_producer.dto.OrderEvent;
import com.ActiveMQ_PubSub_Practice.order_producer.service.OrderPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderPublisher publisher;

    @PostMapping
    public String createOrder(@RequestBody OrderEvent orderEvent) throws JsonProcessingException {

        publisher.publish(orderEvent);

        return "Order Event Published Successfully";
    }
}
