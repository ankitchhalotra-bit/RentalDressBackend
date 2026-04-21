package com.custom_dress.demo.gateway.service.admin;

import com.cloudinary.Cloudinary;
import com.custom_dress.demo.gateway.model.Dress;
import com.custom_dress.demo.gateway.repository.DressRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class AddDressService {

    private final DressRepository dressRepository;
    private final Cloudinary cloudinary;

    public AddDressService(DressRepository dressRepository, Cloudinary cloudinary) {
        this.dressRepository = dressRepository;
        this.cloudinary = cloudinary;
    }

    public Dress addDress(Dress dress, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
            String imageUrl = uploadResult.get("secure_url").toString();
            
            if (dress.getImageUrls() == null) {
                dress.setImageUrls(new ArrayList<>());
            }
            dress.getImageUrls().add(imageUrl);
        }

        dress.setCreatedAt(System.currentTimeMillis());
        dress.setUpdatedAt(System.currentTimeMillis());
        return dressRepository.save(dress);
    }
}


