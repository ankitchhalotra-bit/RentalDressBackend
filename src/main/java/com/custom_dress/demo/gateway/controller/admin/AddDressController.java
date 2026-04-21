package com.custom_dress.demo.gateway.controller.admin;

import com.custom_dress.demo.gateway.model.Dress;
import com.custom_dress.demo.gateway.service.admin.AddDressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

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
            @RequestParam("occasion") String occasion,
            @RequestParam("rentalPricePerDay") Double rentalPricePerDay,
            @RequestParam("depositAmount") Double depositAmount,
            @RequestParam("description") String description,
            @RequestParam("totalStock") Integer totalStock,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Dress dress = new Dress();
            dress.setName(name);
            dress.setOccasion(occasion);
            dress.setRentalPricePerDay(rentalPricePerDay);
            dress.setDepositAmount(depositAmount);
            dress.setDescription(description);
            dress.setTotalStock(totalStock);
            dress.setAvailableStock(totalStock);
            dress.setImageUrls(new ArrayList<>());
            dress.setActive(true);

            Dress saved = addDressService.addDress(dress, file);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Dress added successfully", "data", saved));

        } catch (Exception e) {
            log.error("Error adding dress: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error adding dress: " + e.getMessage()));
        }
    }
}
