package com.custom_dress.demo.gateway.repository;

import com.custom_dress.demo.gateway.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByUserId(String userId);
    List<Booking> findByDressId(String dressId);
    List<Booking> findByStatus(String status);
}

