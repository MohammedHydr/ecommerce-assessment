package com.example.ecommercequery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItem {
    private Long itemId;

    @DBRef
    private Product product;
    private int quantity;
    private double price;

}
