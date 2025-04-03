package com.shopspree.microservices.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRequest {
    private String id;
    private String name;
    private String description;
    private String skuCode;
    private BigDecimal price;
}
