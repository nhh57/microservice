package com.example.productservice.controller;

import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.BaseResponse;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productSer;

    @RequestMapping(value = "/a", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> createProduct(@RequestBody ProductRequest request){
        log.info("START CREATEPRODUCT");
        BaseResponse response = new BaseResponse();
    productSer.createProduct(request);
        log.info("END CREATEPRODUCT");
        return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> getAllProduct(){
        log.info("START GETALLPRODUCT");
        BaseResponse response = new BaseResponse();
        response.setData(productSer.getAllProducts());
        log.info("END GETALLPRODUCT");
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }
}
