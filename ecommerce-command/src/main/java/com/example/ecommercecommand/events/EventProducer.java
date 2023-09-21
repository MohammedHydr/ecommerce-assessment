package com.example.ecommercecommand.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public <T> void sendEvent(String topic, String eventType, T payload) {
        try {
            ObjectEvent<T> objectEvent = new ObjectEvent<>(eventType, payload);
            kafkaTemplate.send(topic, objectEvent).addCallback(
                    success -> logger.info("Successfully sent {} event", eventType),
                    failure -> logger.error("Failed to send {} event", eventType)
            );
        } catch (Exception e) {
            logger.error("An exception occurred while sending {} event: {}", eventType, e.getMessage());
        }
    }
}
