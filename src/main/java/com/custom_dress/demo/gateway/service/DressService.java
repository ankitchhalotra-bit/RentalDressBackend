package com.custom_dress.demo.gateway.service;

import com.custom_dress.demo.gateway.model.Dress;
import com.custom_dress.demo.gateway.repository.DressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DressService {

    private final DressRepository dressRepository;

    public DressService(DressRepository dressRepository) {
        this.dressRepository = dressRepository;
    }

    public List<Dress> getAllDresses() {
        return dressRepository.findByActiveTrue();
    }

    public Dress getDressById(String id) {
        return dressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dress not found with ID: " + id));
    }

    public List<Dress> getDressesByCategory(String categoryId) {
        return dressRepository.findByCategoryId(categoryId);
    }

    public List<Dress> searchDresses(String searchTerm) {
        return dressRepository.searchByNameOrDescription(searchTerm);
    }

    public List<Dress> filterByOccasion(String occasion) {
        return dressRepository.findByOccasion(occasion);
    }

    public List<Dress> filterByPriceRange(Double minPrice, Double maxPrice) {
        return dressRepository.findByPriceRange(minPrice, maxPrice);
    }

    public Dress createDress(Dress dress) {
        dress.setCreatedAt(System.currentTimeMillis());
        dress.setUpdatedAt(System.currentTimeMillis());
        dress.setActive(true);
        return dressRepository.save(dress);
    }

    public Dress updateDress(String id, Dress dressDetails) {
        Dress dress = getDressById(id);
        
        if (dressDetails.getName() != null) dress.setName(dressDetails.getName());
        if (dressDetails.getDescription() != null) dress.setDescription(dressDetails.getDescription());
        if (dressDetails.getCategoryId() != null) dress.setCategoryId(dressDetails.getCategoryId());
        if (dressDetails.getRentalPricePerDay() != null) dress.setRentalPricePerDay(dressDetails.getRentalPricePerDay());
        if (dressDetails.getDepositAmount() != null) dress.setDepositAmount(dressDetails.getDepositAmount());
        if (dressDetails.getImageUrls() != null) dress.setImageUrls(dressDetails.getImageUrls());
        if (dressDetails.getSizes() != null) dress.setSizes(dressDetails.getSizes());
        if (dressDetails.getColors() != null) dress.setColors(dressDetails.getColors());
        if (dressDetails.getAvailableStock() != null) dress.setAvailableStock(dressDetails.getAvailableStock());
        if (dressDetails.getTotalStock() != null) dress.setTotalStock(dressDetails.getTotalStock());
        if (dressDetails.getCondition() != null) dress.setCondition(dressDetails.getCondition());
        if (dressDetails.getOccasion() != null) dress.setOccasion(dressDetails.getOccasion());
        if (dressDetails.getMaterial() != null) dress.setMaterial(dressDetails.getMaterial());
        
        dress.setUpdatedAt(System.currentTimeMillis());
        return dressRepository.save(dress);
    }

    public void deleteDress(String id) {
        Dress dress = getDressById(id);
        dress.setActive(false);
        dressRepository.save(dress);
    }

    public Dress updateStock(String id, Integer newStock) {
        Dress dress = getDressById(id);
        dress.setAvailableStock(newStock);
        dress.setUpdatedAt(System.currentTimeMillis());
        return dressRepository.save(dress);
    }

    public boolean isAvailable(String dressId, Integer requiredStock) {
        Dress dress = getDressById(dressId);
        return dress.getAvailableStock() >= requiredStock;
    }
}

