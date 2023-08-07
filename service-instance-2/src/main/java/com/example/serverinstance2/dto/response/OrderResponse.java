package com.example.serverinstance2.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
