package com.example.orderservice.controller;

import com.example.orderservice.dto.request.OrderRequest;
import com.example.orderservice.dto.response.BaseResponse;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j

public class OrderController {
private final OrderService orderSer;
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> createOrder(@RequestBody OrderRequest request){
        log.info("START CREATEPRODUCT");
        log.info("OrderRequest: "+request.getOrderLineItemDtoList());
        BaseResponse response = new BaseResponse();
        orderSer.placeOrder(request);
        log.info("END CREATEPRODUCT");
        return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
    }
}
