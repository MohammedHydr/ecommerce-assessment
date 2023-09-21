package com.example.ecommercecommand.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSold {
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private int quantitySold;
    private int remainingStock;
    private boolean stockStatus;
    private Long orderId;
    private Long customerId;
    private BigDecimal productTotalPrice;
}