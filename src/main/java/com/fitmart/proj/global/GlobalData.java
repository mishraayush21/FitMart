package com.fitmart.proj.global;

import com.fitmart.proj.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Product> cart;
    static {
        cart = new ArrayList<>();
    }
    public static final String TOPIC_NAME = "topic";
    public static final String TOPIC_ADDED_TO_CART = "topic_added_to_cart";
    public static final String TOPIC_PRODUCTS_SOLD = "topic_products_sold";
    public static final String TOPIC_ITEM_VIEWED = "topic_item_viewed";
    public static final String TOPIC_PAYMENT_STATUS = "topic_payment_status";


}
