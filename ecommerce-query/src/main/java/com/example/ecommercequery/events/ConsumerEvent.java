package com.example.ecommercequery.events;

import lombok.Data;

@Data
public class ConsumerEvent<T> {
    private String eventType;
    private T payload;

    public ConsumerEvent(){}

    public ConsumerEvent(String eventType, T payload) {
        this.eventType = eventType;
        this.payload = payload;
    }
}