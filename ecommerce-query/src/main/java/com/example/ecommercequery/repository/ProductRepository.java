package com.example.ecommercequery.repository;

import com.example.ecommercequery.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, Long> {

}
