package com.shopspree.microservices.service;

import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    public boolean isInStock(String skuCode,Integer quantity);


}
