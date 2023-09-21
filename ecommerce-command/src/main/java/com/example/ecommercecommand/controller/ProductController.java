package com.example.ecommercecommand.controller;

import com.example.ecommercecommand.model.Product;
import com.example.ecommercecommand.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create a new product
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // Update an existing product
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        productService.updateProduct(id, updatedProduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Delete a product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
