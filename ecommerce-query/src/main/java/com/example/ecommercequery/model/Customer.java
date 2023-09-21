package com.example.ecommercequery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "customers")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
    @Id
    @Field("customer_id")
    private Long customerId;

    private String name;
    private String email;
    private String phoneNumber;
}