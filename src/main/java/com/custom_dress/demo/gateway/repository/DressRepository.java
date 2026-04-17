package com.custom_dress.demo.gateway.repository;

import com.custom_dress.demo.gateway.model.AddDressInfoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DressRepository extends MongoRepository<AddDressInfoDTO, String> {
}
