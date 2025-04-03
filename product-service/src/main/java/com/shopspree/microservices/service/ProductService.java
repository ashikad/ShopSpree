package com.shopspree.microservices.service;

import com.shopspree.microservices.dto.ProductRequest;
import com.shopspree.microservices.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    public ProductResponse createProduct(ProductRequest productRequest);


    List<ProductResponse> getAllProducts();
}
