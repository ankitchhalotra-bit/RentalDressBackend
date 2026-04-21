package com.custom_dress.demo.gateway.repository;

import com.custom_dress.demo.gateway.model.Dress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DressRepository extends MongoRepository<Dress, String> {
    List<Dress> findByCategoryId(String categoryId);
    List<Dress> findByActiveTrue();
    
    @Query("{ 'occasion': ?0 }")
    List<Dress> findByOccasion(String occasion);
    
    @Query("{ 'active': true, 'rentalPricePerDay': { $gte: ?0, $lte: ?1 } }")
    List<Dress> findByPriceRange(Double minPrice, Double maxPrice);
    
    @Query("{ 'active': true, $or: [ { 'name': { $regex: ?0, $options: 'i' } }, { 'description': { $regex: ?0, $options: 'i' } } ] }")
    List<Dress> searchByNameOrDescription(String searchTerm);
}
