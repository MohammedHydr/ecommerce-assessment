package com.example.ecommercecommand.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(nullable = false)
    private String name;

    private String email;

    @Column(length = 15)
    private String phoneNumber;

}
