package com.example.ecommercequery.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private String name;
    private BigDecimal price;
    private int stock;
    private boolean stockStatus;
}
