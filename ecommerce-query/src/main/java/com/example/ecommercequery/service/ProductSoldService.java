package com.example.ecommercequery.service;

import com.example.ecommercequery.model.ProductSold;
import com.example.ecommercequery.repository.ProductSoldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSoldService {

    @Autowired
    private ProductSoldRepository productSoldRepository;

    public List<ProductSold> getAllProductsSold() {
        return productSoldRepository.findAll();
    }
}

