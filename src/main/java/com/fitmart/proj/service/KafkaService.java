package com.fitmart.proj.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitmart.proj.global.GlobalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public boolean itemAddedToCart(HashMap<String, String> cartItem) {
        try {
            String cartItemJson = objectMapper.writeValueAsString(cartItem);
            this.kafkaTemplate.send(GlobalData.TOPIC_ADDED_TO_CART, cartItemJson);
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean itemsSold(List<HashMap<String, String>> product) {
        try {
            String productJson = objectMapper.writeValueAsString(product);
            this.kafkaTemplate.send(GlobalData.TOPIC_PRODUCTS_SOLD, productJson);
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean itemViewed(HashMap<String, String> item) {
        try {
            String itemJson = objectMapper.writeValueAsString(item);
            this.kafkaTemplate.send(GlobalData.TOPIC_ITEM_VIEWED, itemJson);
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean paymentStatus(HashMap<String, String> status) {
        try {
            String statusJson = objectMapper.writeValueAsString(status);
            this.kafkaTemplate.send(GlobalData.TOPIC_PAYMENT_STATUS, statusJson);
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
