package com.custom_dress.demo.gateway.controller.admin;

import com.custom_dress.demo.gateway.model.AddDressInfoDTO;
import com.custom_dress.demo.gateway.service.admin.AddDressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AddDressController extends MessageController {

    private final AddDressService addDressService;

    public AddDressController(AddDressService addDressService) {
        this.addDressService = addDressService;
    }

    @PostMapping(value = "/dress", consumes = "multipart/form-data")
    public ResponseEntity<?> addDress(
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file) {
        try {
            AddDressInfoDTO dto = new AddDressInfoDTO();
            dto.setDressID(UUID.randomUUID().toString());
            dto.setName(name);
            dto.setType(type);
            dto.setPrice(price);
            dto.setDescription(description);
            dto.setFavorite(false);
            dto.setAddCart(false);

            AddDressInfoDTO saved = addDressService.addDress(dto, file);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Dress added successfully", "data", saved));

        } catch (Exception e) {
            log.error("Error adding dress: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error adding dress: " + e.getMessage()));
        }
    }
}
