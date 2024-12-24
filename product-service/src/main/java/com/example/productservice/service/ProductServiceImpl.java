package com.example.productservice.service;

import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.dto.response.TicketDetailResponse;
import com.example.productservice.model.TicketDetail;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
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

//    public void createProduct(ProductRequest productRequest){
//        TicketDetail product = TicketDetail.builder()
//                .name(productRequest.getName())
//                .description(productRequest.getDescription())
//                .price(productRequest.getPrice())
//                .build();
//        productRepo.save(product);
//        log.info("Product "+product.getId()+ " is saved",product.getId());
//    }

    public List<TicketDetailResponse> getAllProducts(){
        List<TicketDetail> products = productRepo.findAll();
       return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    @Override
    public Page<TicketDetailResponse> getAllProducts(String keyword, int categoryId, PageRequest pageRequest) throws Exception {
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber()-1, pageRequest.getPageSize());
        Page<TicketDetail> pageProduct=productRepo.getAllProducts(keyword,pageable);
        return pageProduct.map(TicketDetailResponse::fromProduct);
    }

    private TicketDetailResponse mapToProductResponse(TicketDetail ticketDetail){
return TicketDetailResponse.fromProduct(ticketDetail);
    }
}
