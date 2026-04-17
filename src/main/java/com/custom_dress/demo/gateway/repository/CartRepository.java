package com.custom_dress.demo.gateway.repository;

import com.custom_dress.demo.gateway.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByUserId(String userId);
}
