package com.custom_dress.demo.gateway.controller.admin;

import com.custom_dress.demo.gateway.service.admin.DeleteDressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class DeleteDressController extends MessageController {

    private final DeleteDressService deleteDressService;

    public DeleteDressController(DeleteDressService deleteDressService) {
        this.deleteDressService = deleteDressService;
    }

    @DeleteMapping("/dress/{id}")
    public ResponseEntity<?> deleteDress(@PathVariable String id) throws Exception {
        deleteDressService.deleteById(id);
        return ResponseEntity.ok("Dress deleted successfully");
    }
}
