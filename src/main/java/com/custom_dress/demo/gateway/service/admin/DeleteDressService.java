package com.custom_dress.demo.gateway.service.admin;

import com.cloudinary.Cloudinary;
import com.custom_dress.demo.gateway.model.AddDressInfoDTO;
import com.custom_dress.demo.gateway.repository.DressRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeleteDressService {
    public final DressRepository addDressRepo;
    public final Cloudinary cloudinary;

    public DeleteDressService(DressRepository addDressRepo, Cloudinary cloudinary) {
        this.addDressRepo = addDressRepo;
        this.cloudinary = cloudinary;
    }

    public void deleteById(String id) throws Exception {

        // 1️⃣ Find dress
        AddDressInfoDTO dress = addDressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Dress not found"));

        // 2️⃣ Delete image from Cloudinary
        if (dress.getPublicId() != null) {
            cloudinary.uploader().destroy(dress.getPublicId(), Map.of());
        }

        // 3️⃣ Delete from MongoDB
        addDressRepo.deleteById(id);
    }
}
