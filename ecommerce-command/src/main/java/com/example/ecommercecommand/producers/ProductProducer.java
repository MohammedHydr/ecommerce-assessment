package com.example.ecommercecommand.producers;

import com.example.ecommercecommand.dto.ProductSold;
import com.example.ecommercecommand.events.EventProducer;
import com.example.ecommercecommand.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.ecommercecommand.constants.KafkaTopicsNames.*;

@Service
public class ProductProducer {

    @Autowired
    private EventProducer eventProducer;

    public void sendProductCreatedEvent(Product product) {
        eventProducer.sendEvent(PRODUCT_CREATED, PRODUCT_CREATED, product);
    }

    public void sendProductUpdatedEvent(Product product) {
        eventProducer.sendEvent(PRODUCT_UPDATED, PRODUCT_UPDATED, product);
    }

    public void sendProductSoldEvent(ProductSold productSold) {
        eventProducer.sendEvent(PRODUCT_SOLD, PRODUCT_SOLD, productSold);
    }

    public void sendProductDeletedEvent(Long productId) {
        eventProducer.sendEvent(PRODUCT_DELETED, PRODUCT_DELETED, productId);
    }
}
