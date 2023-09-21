package com.example.ecommercequery.repository;

import com.example.ecommercequery.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Long> {
}
