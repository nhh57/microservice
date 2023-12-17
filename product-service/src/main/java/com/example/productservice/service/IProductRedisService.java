package com.example.productservice.service;


import com.example.productservice.dto.response.ProductResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductRedisService {
    //Clear cache data in Redis
    void clear();

    List<ProductResponse> getAllProducts(
            String keyword,
            int categoryId,
            PageRequest pageRequest
    ) throws JsonProcessingException;

    void saveAllProducts(List<ProductResponse> productResponses,
                         String keyword,
                         int categoryId,
                         PageRequest pageRequest) throws JsonProcessingException;
}
