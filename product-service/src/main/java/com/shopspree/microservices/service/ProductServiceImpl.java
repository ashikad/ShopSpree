package com.shopspree.microservices.service;

import com.shopspree.microservices.dto.ProductRequest;
import com.shopspree.microservices.dto.ProductResponse;
import com.shopspree.microservices.model.Product;
import com.shopspree.microservices.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .skuCode(productRequest.getSkuCode())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product created successfully");

        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());

    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> products = productRepository.findAll()
                .stream().map(product -> new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice()))
                .collect(Collectors.toList());

        return products;
    }
}
