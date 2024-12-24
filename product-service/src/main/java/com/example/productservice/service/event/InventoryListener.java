package com.example.productservice.service.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class InventoryListener {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public InventoryListener(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "dbserver1.testdata.t_inventory", groupId = "inventory-listener-group")
    public void listen(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            JsonNode afterNode = jsonNode.path("payload").path("after");

            if (!afterNode.isMissingNode()) {
                String skuCode = afterNode.path("sku_code").asText();
                if (skuCode != null && !skuCode.isEmpty()) {
                    String cacheKey = "product::" + skuCode;
                    redisTemplate.delete(cacheKey);
                    System.out.println("Cache deleted for product: " + skuCode);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
