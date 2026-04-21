package com.custom_dress.demo.gateway.controller.admin;

import com.custom_dress.demo.gateway.model.Dress;
import com.custom_dress.demo.gateway.service.admin.UpdateDressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UpdateDressController extends MessageController {

    private final UpdateDressService updateDressService;

    public UpdateDressController(UpdateDressService updateDressService) {
        this.updateDressService = updateDressService;
    }

    @PutMapping("/admin/dress/{id}")
    public ResponseEntity<Dress> updateDress(
            @PathVariable String id,
            @ModelAttribute Dress dress,
            @RequestParam(required = false) MultipartFile file
    ) throws Exception {
        Dress updatedDress = updateDressService.updateDress(id, dress, file);
        return ResponseEntity.ok(updatedDress);
    }
}
