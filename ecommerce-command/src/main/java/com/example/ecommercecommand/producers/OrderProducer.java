package com.example.ecommercecommand.producers;

import com.example.ecommercecommand.events.EventProducer;
import com.example.ecommercecommand.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.ecommercecommand.constants.KafkaTopicsNames.ORDER_CREATED;
import static com.example.ecommercecommand.constants.KafkaTopicsNames.ORDER_DELETED;

@Service
public class OrderProducer {

    @Autowired
    private EventProducer eventProducer;

    public void sendOrderCreatedEvent(Order order) {
        eventProducer.sendEvent(ORDER_CREATED, ORDER_CREATED, order);
    }

    public void sendOrderDeletedEvent(Long orderId) {
        eventProducer.sendEvent(ORDER_DELETED, ORDER_DELETED, orderId);
    }
}
