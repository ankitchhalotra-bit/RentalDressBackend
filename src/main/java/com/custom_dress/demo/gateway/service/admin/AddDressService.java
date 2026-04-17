package com.custom_dress.demo.gateway.service.admin;

import com.cloudinary.Cloudinary;
import com.custom_dress.demo.gateway.model.AddDressInfoDTO;
import com.custom_dress.demo.gateway.repository.DressRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class AddDressService {

    private final DressRepository dressRepository;
    private final Cloudinary cloudinary;

    public AddDressService(DressRepository dressRepository, Cloudinary cloudinary) {
        this.dressRepository = dressRepository;
        this.cloudinary = cloudinary;
    }

    public AddDressInfoDTO addDress(AddDressInfoDTO dress, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());

            dress.setFile(uploadResult.get("secure_url").toString());
            dress.setPublicId(uploadResult.get("public_id").toString());
        }

        return dressRepository.save(dress);
    }
}
