package com.example.ecommercecommand.events;

import lombok.Data;

@Data
public class ObjectEvent<T> {
    private String eventType;
    private T payload;

    public ObjectEvent(String eventType, T payload) {
        this.eventType = eventType;
        this.payload = payload;
    }
}
