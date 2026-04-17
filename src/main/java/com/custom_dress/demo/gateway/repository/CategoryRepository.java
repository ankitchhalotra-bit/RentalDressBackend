package com.custom_dress.demo.gateway.repository;

import com.custom_dress.demo.gateway.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
