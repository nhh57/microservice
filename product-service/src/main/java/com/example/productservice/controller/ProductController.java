package com.example.productservice.controller;


import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.BaseResponse;
import com.example.productservice.dto.response.ProductResponse;
import com.example.productservice.service.IProductRedisService;
import com.example.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productSer;
    private final IProductRedisService productRedisService;
    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping(value = "/a", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> createProduct(@RequestBody ProductRequest request) {
        log.info("START CREATEPRODUCT");
        BaseResponse response = new BaseResponse();
        productSer.createProduct(request);
        log.info("END CREATEPRODUCT");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> getAllProduct() {
        log.info("START GETALLPRODUCT");
        BaseResponse response = new BaseResponse();
        response.setData(productSer.getAllProducts());
        log.info("END GETALLPRODUCT");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/redis", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> getProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "") int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {
        log.info("com/example/productservice/controller/ProductController.java - getProducts - START ");
        BaseResponse baseResponse = new BaseResponse();
        int totalPages = 0;
        // Tạo Pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());
        log.info(String.format("keyword=%s, category_id= %d, page= %d, limit=%d", keyword, categoryId, page, limit));
        List<ProductResponse> productResponse = productRedisService.getAllProducts(keyword, categoryId, pageRequest);
        log.info("Product: " + productResponse);
        if (productResponse == null) {
            Page<ProductResponse> productPage = productSer.getAllProducts(keyword, categoryId, pageRequest);
            totalPages = productPage.getTotalPages();
            productResponse = productPage.getContent();
            productRedisService.saveAllProducts(productResponse,
                    keyword,
                    categoryId,
                    pageRequest);
        }
        baseResponse.setData(productResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


    @PostMapping("/remove-cache")
    public void removeCache() {
        log.info("START REMOVEPRODUCT");
        productRedisService.clear();
    }
}