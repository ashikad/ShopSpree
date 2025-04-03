package com.shopspree.microservices.order.service;

import com.shopspree.microservices.order.dto.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    public void placeOrder(OrderRequest orderRequest);
}
