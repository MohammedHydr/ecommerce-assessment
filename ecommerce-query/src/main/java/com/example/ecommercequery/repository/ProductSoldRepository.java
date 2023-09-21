package com.example.ecommercequery.repository;


import com.example.ecommercequery.model.ProductSold;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductSoldRepository extends MongoRepository<ProductSold, String> {
}
