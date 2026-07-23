package com.ActiveMQ_PubSub_Practice.email_consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {

    private Long orderId;
    private String customerName;
    private String productName;
    private Integer quantity;
    private Double price;
}