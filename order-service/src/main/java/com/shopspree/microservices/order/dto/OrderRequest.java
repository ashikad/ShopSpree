package com.shopspree.microservices.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String orderNumber;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
    private UserDetails userDetails;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDetails {
        private String email;
        private String firstName;
        private String lastName;
    }
}
