package com.example.ecommercequery.controller;

import com.example.ecommercequery.service.AnalyticsService;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService; // Assuming the service interface is named AnalyticsService

    @GetMapping("/totalSales")
    public double getTotalSales() {
        return analyticsService.getTotalSales();
    }

    @GetMapping("/productTotals")
    public List<BasicDBObject> getTopSellingProduct() {
        return analyticsService.getAllProductsSalesTotals();
    }
}
