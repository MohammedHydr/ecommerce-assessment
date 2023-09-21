package com.example.ecommercecommand.service;

import com.example.ecommercecommand.dto.ProductSold;
import com.example.ecommercecommand.exceptions.CustomerNotFoundException;
import com.example.ecommercecommand.exceptions.InsufficientStockException;
import com.example.ecommercecommand.exceptions.OrderNotFoundException;
import com.example.ecommercecommand.exceptions.ProductNotFoundException;
import com.example.ecommercecommand.model.Customer;
import com.example.ecommercecommand.model.Order;
import com.example.ecommercecommand.model.OrderItem;
import com.example.ecommercecommand.model.Product;
import com.example.ecommercecommand.producers.OrderProducer;
import com.example.ecommercecommand.producers.ProductProducer;
import com.example.ecommercecommand.repository.CustomerRepository;
import com.example.ecommercecommand.repository.OrderRepository;
import com.example.ecommercecommand.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductProducer productProducer; // Your Kafka producer service

    @Autowired
    private OrderProducer orderProducer;

    // The @Transactional annotation ensures that this method runs in a single transaction.
    // That means if anything goes wrong while saving the order or updating the product stock,
    // the database changes will be rolled back, keeping our data in a consistent state.
    @Transactional
    public void saveOrder(Order order) {
        try {
            BigDecimal totalPrice = BigDecimal.ZERO;
            order.setOrderDate(new Date());
            for (OrderItem item : order.getOrderItems()) {
                item.setOrder(order);
            }
            Order savedOrder = orderRepository.save(order);
            // Fetch the Customer object by its ID
            Customer customer = customerRepository.findById(savedOrder.getCustomer().getCustomerId())
                    .orElseThrow(() -> new CustomerNotFoundException(savedOrder.getCustomer().getCustomerId()));
            List<OrderItem> items = savedOrder.getOrderItems();
            for (OrderItem item : items) {
                try {
                    // Fetch the Product object by its ID
                    Product product = productRepository.findById(item.getProduct().getProductId())
                            .orElseThrow(() -> new ProductNotFoundException(item.getProduct().getProductId()));

                    item.setPrice(product.getPrice());
                    item.setProduct(product);
                    BigDecimal itemTotalPrice = product.getPrice().multiply(new BigDecimal(item.getQuantity()));
                    totalPrice = totalPrice.add(itemTotalPrice);
                    int newQuantity = product.getStock() - item.getQuantity();
                    if (newQuantity < 0) {
                        throw new InsufficientStockException(product.getName());
                    }
                    product.setStock(newQuantity);
                    productProducer.sendProductUpdatedEvent(product);
                    productRepository.save(product);
                    // Create and send ProductSold event to Kafka
                    ProductSold productSold = new ProductSold();
                    productSold.setProductId(product.getProductId());
                    productSold.setProductName(product.getName());
                    productSold.setProductPrice(product.getPrice());
                    productSold.setQuantitySold(item.getQuantity());
                    productSold.setRemainingStock(newQuantity);
                    productSold.setStockStatus(product.isStockStatus());
                    productSold.setOrderId(order.getOrderId());
                    productSold.setCustomerId(customer.getCustomerId());
                    productSold.setProductTotalPrice(itemTotalPrice);
                    productProducer.sendProductSoldEvent(productSold);
                } catch (InsufficientStockException e) {
                    logger.error(e.getMessage()); // Logging the exception message as an error
                }
            }
            savedOrder.setCustomer(customer);
            savedOrder.setTotalPrice(totalPrice);
            orderRepository.save(savedOrder);
            orderProducer.sendOrderCreatedEvent(order);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred while creating the order ", e);
        }
    }

    public void deleteOrder(Long id) {
        try {
            if (!orderRepository.existsById(id)) {
                throw new OrderNotFoundException(id);
            }
            orderRepository.deleteById(id);
            orderProducer.sendOrderDeletedEvent(id);
        } catch (ProductNotFoundException e) {
            logger.error(e.getMessage()); // Logging the exception message as an error
            throw e; // Re-throwing the exception
        } catch (Exception e) {
            logger.error("An unexpected exception occurred while deleting the order with id {}", id, e);
        }
    }
}
