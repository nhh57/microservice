package com.example.productservice.service;

import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.TicketDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
//    public void createProduct(ProductRequest productRequest);
    public List<TicketDetailResponse> getAllProducts();
    public Page<TicketDetailResponse> getAllProducts(String keyword,
                                                     int categoryId,
                                                     PageRequest pageRequest) throws Exception;
}
