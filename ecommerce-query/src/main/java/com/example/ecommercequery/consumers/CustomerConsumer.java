package com.example.ecommercequery.consumers;

import com.example.ecommercequery.events.ConsumerEvent;
import com.example.ecommercequery.model.Customer;
import com.example.ecommercequery.repository.CustomerRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.example.ecommercequery.constants.KafkaConsumerConstants.*;

@Service
public class CustomerConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CustomerConsumer.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = CUSTOMER_CREATED, groupId = CONSUMER_GROUP_ID)
    public void consumeCustomerCreated(String customerCreatedMessage) {
        try {
            // Deserialize the message as an Event<Customer>
            ConsumerEvent<Customer> event = objectMapper.readValue(customerCreatedMessage, new TypeReference<>() {});
            // Access the payload (Customer object) from the event
            Customer customer = event.getPayload();
            customerRepository.save(customer);
            logger.info("Successfully created customer with ID: {}", customer.getCustomerId());
        } catch (Exception e) {
            logger.error("An error occurred while processing a CustomerCreated event: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = CUSTOMER_UPDATED, groupId = CONSUMER_GROUP_ID)
    public void consumeCustomerUpdated(String customerUpdatedMessage) {
        try {
            // Deserialize the message as an Event<Customer>
            ConsumerEvent<Customer> event = objectMapper.readValue(customerUpdatedMessage, new TypeReference<>() {});
            // Access the payload (Customer object) from the event
            Customer updatedCustomer = event.getPayload();

            Customer existingCustomer = customerRepository.findById(updatedCustomer.getCustomerId()).orElse(null);

            if (existingCustomer != null) {
                existingCustomer.setName(updatedCustomer.getName());
                existingCustomer.setEmail(updatedCustomer.getEmail());
                existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
                customerRepository.save(existingCustomer);
                logger.info("Successfully updated customer with ID: {}", existingCustomer.getCustomerId());
            } else {
                logger.error("Could not find existing customer with ID: {}", updatedCustomer.getCustomerId());
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing a CustomerUpdated event: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = CUSTOMER_DELETED, groupId = CONSUMER_GROUP_ID)
    public void consumeCustomerDeleted(String customerDeletedMessage) {
        try {
            ConsumerEvent<Long> event = objectMapper.readValue(customerDeletedMessage, new TypeReference<>() {});
            Long customerId = event.getPayload();
            customerRepository.deleteById(customerId);
            logger.info("Successfully deleted customer with ID: {}", customerId);
        } catch (Exception e) {
            logger.error("An error occurred while processing a CustomerDeleted event: {}", e.getMessage(), e);
        }
    }
}
