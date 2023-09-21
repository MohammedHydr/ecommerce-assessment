package com.example.ecommercequery.controller;

import com.example.ecommercequery.model.ProductSold;
import com.example.ecommercequery.service.ProductSoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductSoldController {

    @Autowired
    private ProductSoldService productSoldService;

    @GetMapping("/productsold")
    public ResponseEntity<List<ProductSold>> getAllProductSolds() {
        return new ResponseEntity<>(productSoldService.getAllProductsSold(), HttpStatus.OK);
    }
}

