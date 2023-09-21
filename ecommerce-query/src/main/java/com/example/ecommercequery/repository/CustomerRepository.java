package com.example.ecommercequery.repository;

import com.example.ecommercequery.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, Long> {
}
