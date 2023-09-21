package com.example.ecommercequery.constants;

public class KafkaConsumerConstants {

    // Prevent instantiation (constructor delegation)
    private KafkaConsumerConstants(){}

    //TOPICS
    //PRODUCT
    public static final String PRODUCT_CREATED = "ProductCreated";
    public static final String PRODUCT_UPDATED = "ProductUpdated";
    public static final String PRODUCT_SOLD = "ProductSold";
    public static final String PRODUCT_DELETED = "ProductDeleted";

    //CUSTOMER
    public static final String CUSTOMER_CREATED = "CustomerCreated";
    public static final String CUSTOMER_UPDATED = "CustomerUpdated";
    public static final String CUSTOMER_DELETED = "CustomerDeleted";

    //ORDER
    public static final String ORDER_CREATED = "OrderCreated";
    public static final String ORDER_DELETED = "OrderDeleted";
    //----------------------------------------------------------------

    //CONSUMER_GROUP_ID
    public static final String CONSUMER_GROUP_ID = "ecommerce-consumer";
}
