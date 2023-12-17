package com.example.productservice.service;

import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    public void createProduct(ProductRequest productRequest);
    public List<ProductResponse> getAllProducts();
    public Page<ProductResponse> getAllProducts(String keyword,
                                                int categoryId,
                                                PageRequest pageRequest) throws Exception;
}
