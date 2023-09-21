package com.example.ecommercequery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product_sold")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSold {
    @Id
    private String id;

    private Long productId;
    private String productName;
    private double productPrice;
    private int quantitySold;
    private int remainingStock;
    private boolean stockStatus;
    private Long orderId;
    private Long customerId;
    private double productTotalPrice;
}