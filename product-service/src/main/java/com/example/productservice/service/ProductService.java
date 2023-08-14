package com.example.productservice.service;

import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository productRepo;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepo.save(product);
        log.info("Product "+product.getId()+ " is saved",product.getId());
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepo.findAll();
       return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
