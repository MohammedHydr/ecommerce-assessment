package com.example.ecommercequery.service;

import com.mongodb.BasicDBObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private MongoTemplate mongoTemplate;


    public double getTotalSales() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group().sum("productTotalPrice").as("totalSales")
        );
        // Execute the aggregation
        AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(
                aggregation,
                "product_sold",
                BasicDBObject.class
        );
        BasicDBObject dbObject = results.getUniqueMappedResult();
        if (dbObject != null) {
            Object totalSales = dbObject.get("totalSales");
            if (totalSales instanceof Number) {
                return ((Number) totalSales).doubleValue();
            }
        }
        return 0.0;
    }

    public List<BasicDBObject> getAllProductsSalesTotals() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("productName").sum("productTotalPrice").as("totalSales"),
                Aggregation.sort(Sort.Direction.DESC, "totalSales"),
                Aggregation.project("totalSales").and("_id").as("productName").andExclude("_id")
        );

        // Execute the aggregation
        AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(
                aggregation,
                "product_sold",
                BasicDBObject.class
        );

        return results.getMappedResults();
    }
}
