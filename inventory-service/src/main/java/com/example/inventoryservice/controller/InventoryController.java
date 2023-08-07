package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.response.BaseResponse;
import com.example.inventoryservice.dto.response.InventoryResponse;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

    private final InventoryService inventorySer;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> isInStock(@RequestParam List<String> skuCode){
        log.info("START isInStock");
        log.info("skuCode: "+skuCode);
        BaseResponse response = new BaseResponse();
        List<InventoryResponse> listData = inventorySer.isInStockIn(skuCode);
        log.info("listData: "+listData);
        response.setData(listData);
        log.info("END isInStock");
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }



}
