package com.custom_dress.demo.gateway.service.admin;

import com.cloudinary.Cloudinary;
import com.custom_dress.demo.gateway.model.AddDressInfoDTO;
import com.custom_dress.demo.gateway.repository.DressRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@Service
public class UpdateDressService {

    private final DressRepository dressRepository;
    private final Cloudinary cloudinary;
    public UpdateDressService(DressRepository dressRepository,Cloudinary cloudinary) {
        this.dressRepository = dressRepository;
        this.cloudinary = cloudinary;
    }

    public AddDressInfoDTO updateDress(String id,
                                       AddDressInfoDTO updatedDress,
                                       MultipartFile file) throws Exception {

        AddDressInfoDTO existingDress = dressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dress not found"));

        // Update fields
        existingDress.setName(updatedDress.getName());
        existingDress.setType(updatedDress.getType());
        existingDress.setPrice(updatedDress.getPrice());
        existingDress.setDescription(updatedDress.getDescription());
        existingDress.setFavorite(updatedDress.isFavorite());
        existingDress.setAddCart(updatedDress.isAddCart());

        // If new file uploaded -> upload to Cloudinary
        if (file != null && !file.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
            existingDress.setFile(uploadResult.get("secure_url").toString());
        }

        return dressRepository.save(existingDress);
    }
}


