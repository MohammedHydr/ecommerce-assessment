package com.example.ecommercecommand.producers;

import com.example.ecommercecommand.events.EventProducer;
import com.example.ecommercecommand.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.ecommercecommand.constants.KafkaTopicsNames.*;

@Service
public class CustomerProducer {

    @Autowired
    private EventProducer eventProducer;

    public void sendCustomerCreatedEvent(Customer customer) {
        eventProducer.sendEvent(CUSTOMER_CREATED, CUSTOMER_CREATED, customer);
    }

    public void sendCustomerUpdatedEvent(Customer customer) {
        eventProducer.sendEvent(CUSTOMER_UPDATED, CUSTOMER_UPDATED, customer);
    }

    public void sendCustomerDeletedEvent(Long customerId) {
        eventProducer.sendEvent(CUSTOMER_DELETED, CUSTOMER_DELETED, customerId);
    }
}
