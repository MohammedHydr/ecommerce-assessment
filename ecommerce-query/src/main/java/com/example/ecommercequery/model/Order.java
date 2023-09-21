package com.example.ecommercequery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "orders")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    @Id
    @Field("order_id")
    private Long orderId;

    private Date orderDate;

    @DBRef
    private Customer customer;

    private List<OrderItem> orderItems = new ArrayList<>();
    private double totalPrice;
}