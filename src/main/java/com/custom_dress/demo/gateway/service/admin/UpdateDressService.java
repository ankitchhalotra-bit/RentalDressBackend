package com.custom_dress.demo.gateway.service.admin;

import com.cloudinary.Cloudinary;
import com.custom_dress.demo.gateway.model.Dress;
import com.custom_dress.demo.gateway.repository.DressRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

@Service
public class UpdateDressService {

    private final DressRepository dressRepository;
    private final Cloudinary cloudinary;
    
    public UpdateDressService(DressRepository dressRepository, Cloudinary cloudinary) {
        this.dressRepository = dressRepository;
        this.cloudinary = cloudinary;
    }

    public Dress updateDress(String id,
                             Dress updatedDress,
                             MultipartFile file) throws Exception {

        Dress existingDress = dressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dress not found"));

        // Update fields
        if (updatedDress.getName() != null) existingDress.setName(updatedDress.getName());
        if (updatedDress.getDescription() != null) existingDress.setDescription(updatedDress.getDescription());
        if (updatedDress.getRentalPricePerDay() != null) existingDress.setRentalPricePerDay(updatedDress.getRentalPricePerDay());
        if (updatedDress.getDepositAmount() != null) existingDress.setDepositAmount(updatedDress.getDepositAmount());
        if (updatedDress.getOccasion() != null) existingDress.setOccasion(updatedDress.getOccasion());
        if (updatedDress.getMaterial() != null) existingDress.setMaterial(updatedDress.getMaterial());
        if (updatedDress.getAvailableStock() != null) existingDress.setAvailableStock(updatedDress.getAvailableStock());
        if (updatedDress.getTotalStock() != null) existingDress.setTotalStock(updatedDress.getTotalStock());

        // If new file uploaded -> upload to Cloudinary
        if (file != null && !file.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
            String imageUrl = uploadResult.get("secure_url").toString();
            
            if (existingDress.getImageUrls() == null) {
                existingDress.setImageUrls(new ArrayList<>());
            }
            existingDress.getImageUrls().add(imageUrl);
        }

        existingDress.setUpdatedAt(System.currentTimeMillis());
        return dressRepository.save(existingDress);
    }
}




