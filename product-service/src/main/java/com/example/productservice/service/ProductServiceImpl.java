package com.example.productservice.service;

import com.example.productservice.controller.ProductController;
import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@Transactional

public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepo;
    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

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

    @Override
    public Page<ProductResponse> getAllProducts(String keyword, int categoryId, PageRequest pageRequest) throws Exception {
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber()-1, pageRequest.getPageSize());
        Page<Product> pageProduct=productRepo.getAllProducts(keyword,categoryId,pageable);
        return pageProduct.map(ProductResponse::fromProduct);
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .categoryId(product.getCategoryId())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
