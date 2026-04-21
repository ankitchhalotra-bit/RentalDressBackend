package com.custom_dress.demo.gateway.service.admin;

import com.cloudinary.Cloudinary;
import com.custom_dress.demo.gateway.model.Dress;
import com.custom_dress.demo.gateway.repository.DressRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteDressService {
    public final DressRepository dressRepository;
    public final Cloudinary cloudinary;

    public DeleteDressService(DressRepository dressRepository, Cloudinary cloudinary) {
        this.dressRepository = dressRepository;
        this.cloudinary = cloudinary;
    }

    public void deleteById(String id) throws Exception {

        // 1️⃣ Find dress
        Dress dress = dressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dress not found"));

        // 2️⃣ Soft delete - mark as inactive
        dress.setActive(false);
        dress.setUpdatedAt(System.currentTimeMillis());
        dressRepository.save(dress);
    }
}

