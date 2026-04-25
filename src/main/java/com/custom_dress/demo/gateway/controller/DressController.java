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
    public ResponseEntity<Map<String, Object>> getAllDresses() {
        List<Dress> dresses = dressService.getAllDresses();
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "All available dresses retrieved successfully",
                "data", dresses,
                "totalCount", dresses.size()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getDressById(@PathVariable String id) {
        Dress dress = dressService.getDressById(id);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dress retrieved successfully",
                "data", dress
        ));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Map<String, Object>> getDressesByCategory(@PathVariable String categoryId) {
        List<Dress> dresses = dressService.getDressesByCategory(categoryId);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dresses retrieved by category",
                "data", dresses,
                "totalCount", dresses.size()
        ));
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDresses(@RequestParam String query) {
        List<Dress> results = dressService.searchDresses(query);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Search completed successfully",
                "data", results,
                "totalCount", results.size()
        ));
    }

    @GetMapping("/filter/occasion")
    public ResponseEntity<Map<String, Object>> filterByOccasion(@RequestParam String occasion) {
        List<Dress> dresses = dressService.filterByOccasion(occasion);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dresses retrieved by occasion",
                "data", dresses,
                "totalCount", dresses.size()
        ));
    }

    @GetMapping("/filter/price")
    public ResponseEntity<Map<String, Object>> filterByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        List<Dress> dresses = dressService.filterByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dresses retrieved by price range",
                "data", dresses,
                "totalCount", dresses.size()
        ));
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @PathVariable String id,
            @RequestParam(required = false, defaultValue = "1") Integer requiredStock) {
        boolean isAvailable = dressService.isAvailable(id, requiredStock);
        Dress dress = dressService.getDressById(id);
        
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Availability checked",
                "data", Map.of(
                        "dressId", id,
                        "dressName", dress.getName(),
                        "isAvailable", isAvailable,
                        "availableStock", dress.getAvailableStock(),
                        "requiredStock", requiredStock
                )
        ));
    }

    // Admin endpoints
    @PostMapping
    public ResponseEntity<Map<String, Object>> createDress(@RequestBody Dress dress) {
        Dress created = dressService.createDress(dress);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dress created successfully",
                "data", created
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateDress(@PathVariable String id, @RequestBody Dress dressDetails) {
        Dress updated = dressService.updateDress(id, dressDetails);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dress updated successfully",
                "data", updated
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteDress(@PathVariable String id) {
        dressService.deleteDress(id);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dress deleted successfully"
        ));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Map<String, Object>> updateStock(@PathVariable String id, @RequestBody Map<String, Integer> request) {
        Integer newStock = request.get("availableStock");
        Dress updated = dressService.updateStock(id, newStock);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Stock updated successfully",
                "data", updated
        ));
    }
}

