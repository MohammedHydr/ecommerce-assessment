package com.example.ecommercecommand.service;

import com.example.ecommercecommand.exceptions.ProductNotFoundException;
import com.example.ecommercecommand.model.Product;
import com.example.ecommercecommand.producers.ProductProducer;
import com.example.ecommercecommand.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductProducer productProducer;

    public void saveProduct(Product product) {
        try {
            Product savedProduct = productRepository.save(product);
            productProducer.sendProductCreatedEvent(savedProduct);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred while creating the product ", e);
        }
    }

    public void updateProduct(Long id, Product newProduct) {
        try {
            if (!productRepository.existsById(id)) {
                throw new ProductNotFoundException(id);
            }
            Product product = productRepository.getReferenceById(id);
            product.setName(newProduct.getName());
            product.setPrice(newProduct.getPrice());
            product.setStock(newProduct.getStock());
            Product updatedProduct = productRepository.save(product);
            productProducer.sendProductUpdatedEvent(updatedProduct);
        } catch (ProductNotFoundException e) {
            logger.error(e.getMessage()); // Logging the exception message as an error
            throw e; // Re-throwing the exception
        } catch (Exception e) {
            logger.error("An unexpected exception occurred while updating the product with id {}", id, e);
        }
    }

    public void deleteProduct(Long id) {
        try {
            if (!productRepository.existsById(id)) {
                throw new ProductNotFoundException(id);
            }
            productRepository.deleteById(id);
            productProducer.sendProductDeletedEvent(id);
        } catch (ProductNotFoundException e) {
            logger.error(e.getMessage()); // Logging the exception message as an error
            throw e; // Re-throwing the exception
        } catch (Exception e) {
            logger.error("An unexpected exception occurred while deleting the product with id {}", id, e);
        }
    }
}
