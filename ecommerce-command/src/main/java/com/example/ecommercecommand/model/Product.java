package com.example.ecommercecommand.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;

    @Column(name = "in_stock")  // Defines this as a column and sets its name in the DB table
    private boolean stockStatus;

    //annotations in JPA are used to configure callback methods that get executed right before an entity is
    // either persisted (i.e., saved for the first time) or updated. These annotations can be particularly
    // useful for implementing operations that need to occur before an entity is saved or updated in the database.
    @PrePersist
    @PreUpdate
    public void updateInStockStatus() {
        this.stockStatus = this.stock > 0;
    }
}