package com.example.productservice.service;


import com.example.productservice.dto.response.TicketDetailResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductRedisService {
    //Clear cache data in Redis
    void clear();

    List<TicketDetailResponse> getAllProducts(
            String keyword,
            int categoryId,
            PageRequest pageRequest
    ) throws JsonProcessingException;

    void saveAllProducts(List<TicketDetailResponse> ticketDetailRespons,
                         String keyword,
                         int categoryId,
                         PageRequest pageRequest) throws JsonProcessingException;
}
