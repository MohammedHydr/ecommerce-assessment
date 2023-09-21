package com.example.ecommercequery.consumers;

import com.example.ecommercequery.events.ConsumerEvent;
import com.example.ecommercequery.model.Order;
import com.example.ecommercequery.repository.OrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.ecommercequery.constants.KafkaConsumerConstants.*;

@Service
public class OrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = ORDER_CREATED, groupId = CONSUMER_GROUP_ID)
    @Transactional
    public void consumeOrderCreated(String orderCreatedMessage) {
        try {
            // Deserialize the message as an Event<Customer>
            ConsumerEvent<Order> event = objectMapper.readValue(orderCreatedMessage, new TypeReference<>() {});
            // Access the payload (Customer object) from the event
            Order order = event.getPayload();
            orderRepository.save(order);
            logger.info("Successfully created order with ID: {}", order.getOrderId());
        } catch (Exception e) {
            logger.error("An error occurred while processing an OrderCreated event: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = ORDER_DELETED, groupId = CONSUMER_GROUP_ID)
    @Transactional
    public void consumeOrderDeleted(String orderDeletedMessage) {
        try {
            ConsumerEvent<Long> event = objectMapper.readValue(orderDeletedMessage, new TypeReference<>() {});
            Long orderId = event.getPayload();
            if (!orderRepository.existsById(orderId)) {
                logger.error("Could not find existing order with ID: {}", orderId);
                return;
            }
            orderRepository.deleteById(orderId);
            logger.info("Successfully deleted order with ID: {}", orderId);
        } catch (Exception e) {
            logger.error("An error occurred while processing an OrderDeleted event: {}", e.getMessage(), e);
        }
    }

}
