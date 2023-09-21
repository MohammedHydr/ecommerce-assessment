package com.example.ecommercequery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "products")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @Id
    @Field("product_id")
    private Long productId;

    private String name;
    private double price;
    private int stock;
    @Field("in_stock")
    private boolean stockStatus;
}