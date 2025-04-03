package com.shopspree.microservices.repository;

import com.shopspree.microservices.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductRepository extends MongoRepository<Product,String> {
}
