package com.custom_dress.demo.gateway.controller;

import com.custom_dress.demo.gateway.model.Dress;
import com.custom_dress.demo.gateway.service.DressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/dresses")
public class DressController {

    private final DressService dressService;

    public DressController(DressService dressService) {
        this.dressService = dressService;
    }

    @GetMapping
    public ResponseEntity<List<Dress>> getAllDresses() {
        return ResponseEntity.ok(dressService.getAllDresses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dress> getDressById(@PathVariable String id) {
        return ResponseEntity.ok(dressService.getDressById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Dress>> getDressesByCategory(@PathVariable String categoryId) {
        return ResponseEntity.ok(dressService.getDressesByCategory(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Dress>> searchDresses(@RequestParam String query) {
        return ResponseEntity.ok(dressService.searchDresses(query));
    }

    @GetMapping("/filter/occasion")
    public ResponseEntity<List<Dress>> filterByOccasion(@RequestParam String occasion) {
        return ResponseEntity.ok(dressService.filterByOccasion(occasion));
    }

    @GetMapping("/filter/price")
    public ResponseEntity<List<Dress>> filterByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        return ResponseEntity.ok(dressService.filterByPriceRange(minPrice, maxPrice));
    }

    // Admin endpoints
    @PostMapping
    public ResponseEntity<Dress> createDress(@RequestBody Dress dress) {
        return ResponseEntity.ok(dressService.createDress(dress));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dress> updateDress(@PathVariable String id, @RequestBody Dress dressDetails) {
        return ResponseEntity.ok(dressService.updateDress(id, dressDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDress(@PathVariable String id) {
        dressService.deleteDress(id);
        return ResponseEntity.ok(Map.of("message", "Dress deleted successfully"));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Dress> updateStock(@PathVariable String id, @RequestBody Map<String, Integer> request) {
        Integer newStock = request.get("availableStock");
        return ResponseEntity.ok(dressService.updateStock(id, newStock));
    }
}

