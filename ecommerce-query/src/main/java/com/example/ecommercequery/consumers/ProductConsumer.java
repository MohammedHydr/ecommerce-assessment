package com.example.ecommercequery.consumers;

import com.example.ecommercequery.events.ConsumerEvent;
import com.example.ecommercequery.model.Product;
import com.example.ecommercequery.model.ProductSold;
import com.example.ecommercequery.repository.ProductRepository;
import com.example.ecommercequery.repository.ProductSoldRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.example.ecommercequery.constants.KafkaConsumerConstants.*;

@Service
public class ProductConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ProductConsumer.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSoldRepository productSoldRepository;

    // The ObjectMapper is used for deserialization of the incoming JSON message from Kafka topics into a Java object of type Product
    @Autowired
    private ObjectMapper objectMapper;


    @KafkaListener(topics = PRODUCT_CREATED, groupId = CONSUMER_GROUP_ID)
    public void consumeProductCreated(String productCreatedMessage) {
        try {
            // Deserialize the message as an Event<Customer>
            ConsumerEvent<Product> event = objectMapper.readValue(productCreatedMessage, new TypeReference<>() {});
            // Access the payload (Customer object) from the event
            Product product = event.getPayload();
            productRepository.save(product);
            logger.info("Successfully created product with ID: {}", product.getProductId());
        } catch (Exception e) {
            logger.error("An error occurred while processing a ProductCreated event: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = PRODUCT_UPDATED, groupId = CONSUMER_GROUP_ID)
    public void consumeProductUpdated(String productUpdatedMessage) {
        try {
            // Deserialize the message as an Event<Customer>
            ConsumerEvent<Product> event = objectMapper.readValue(productUpdatedMessage, new TypeReference<>() {});
            // Access the payload (Customer object) from the event
            Product updatedProduct = event.getPayload();

            Product existingProduct = productRepository.findById(updatedProduct.getProductId()).orElse(null);

            if (existingProduct != null) {
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setPrice(updatedProduct.getPrice());
                existingProduct.setStock(updatedProduct.getStock());
                existingProduct.setStockStatus(updatedProduct.isStockStatus());
                productRepository.save(existingProduct);
                logger.info("Successfully updated product with ID: {}", existingProduct.getProductId());
            } else {
                logger.error("Could not find existing product with ID: {}", updatedProduct.getProductId());
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing a ProductUpdated event: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = PRODUCT_DELETED, groupId = CONSUMER_GROUP_ID)
    public void consumeProductDeleted(String productDeletedMessage) {
        try {
            ConsumerEvent<Long> event = objectMapper.readValue(productDeletedMessage, new TypeReference<>() {});
            Long productId = event.getPayload();
            productRepository.deleteById(productId);
            logger.info("Successfully deleted product with ID: {}", productId);
        } catch (Exception e) {
            logger.error("An error occurred while processing a ProductDeleted event: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = PRODUCT_SOLD, groupId = CONSUMER_GROUP_ID)
    public void consumeProductSold(String productSoldMessage) {
        try {
            // Deserialize the message as an Event<Customer>
            ConsumerEvent<ProductSold> event = objectMapper.readValue(productSoldMessage, new TypeReference<>() {});
            // Access the payload (Customer object) from the event
            ProductSold productSold = event.getPayload();
            productSoldRepository.save(productSold);
            logger.info("Successfully created product sold with ID: {}", productSold.getProductId());
        } catch (Exception e) {
            logger.error("An error occurred while processing a ProductSold event: {}", e.getMessage(), e);
        }
    }
}
