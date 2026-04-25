package com.custom_dress.demo.gateway.controller.admin;

import com.custom_dress.demo.gateway.model.Dress;
import com.custom_dress.demo.gateway.service.admin.GetAllDressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/admin")
public class GetAllDress extends MessageController {

    private final GetAllDressService getAllDress;

    public GetAllDress(GetAllDressService getAllDress) {
        this.getAllDress = getAllDress;
    }

    /**
     * Get all dresses (Admin only)
     * Includes inactive dresses for admin management
     */
    @GetMapping("/dresses")
    public ResponseEntity<Map<String, Object>> getAllDresses() {
        List<Dress> dresses = getAllDress.getAllDressesData();
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "All dresses retrieved successfully",
                "data", dresses,
                "totalCount", dresses.size()
        ));
    }

    /**
     * Get all active dresses (Accessible to all users)
     * Only shows active dresses for customers
     */
    @GetMapping("/dresses/active")
    public ResponseEntity<Map<String, Object>> getAllActiveDresses() {
        List<Dress> activeDresses = getAllDress.getAllActiveDresses();
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Active dresses retrieved successfully",
                "data", activeDresses,
                "totalCount", activeDresses.size()
        ));
    }

    /**
     * Get dresses with pagination and filtering
     */
    @GetMapping("/dresses/page")
    public ResponseEntity<Map<String, Object>> getDressesWithPagination(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String occasion,
            @RequestParam(required = false) String category) {
        Map<String, Object> result = getAllDress.getDressesWithPagination(page, size, occasion, category);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dresses retrieved with pagination",
                "data", result
        ));
    }

    /**
     * Get dress statistics (Admin only)
     */
    @GetMapping("/dresses/stats")
    public ResponseEntity<Map<String, Object>> getDressesStats() {
        Map<String, Object> stats = getAllDress.getDressesStatistics();
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dress statistics retrieved successfully",
                "data", stats
        ));
    }

    /**
     * Search dresses by name or description
     */
    @GetMapping("/dresses/search")
    public ResponseEntity<Map<String, Object>> searchDresses(
            @RequestParam String query) {
        List<Dress> searchResults = getAllDress.searchDresses(query);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Search completed successfully",
                "data", searchResults,
                "totalCount", searchResults.size()
        ));
    }

    /**
     * Filter dresses by category
     */
    @GetMapping("/dresses/category/{categoryId}")
    public ResponseEntity<Map<String, Object>> getDressesByCategory(
            @PathVariable String categoryId) {
        List<Dress> dresses = getAllDress.getDressesByCategory(categoryId);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dresses retrieved by category",
                "data", dresses,
                "totalCount", dresses.size()
        ));
    }

    /**
     * Filter dresses by occasion
     */
    @GetMapping("/dresses/occasion/{occasion}")
    public ResponseEntity<Map<String, Object>> getDressesByOccasion(
            @PathVariable String occasion) {
        List<Dress> dresses = getAllDress.getDressesByOccasion(occasion);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dresses retrieved by occasion",
                "data", dresses,
                "totalCount", dresses.size()
        ));
    }

    /**
     * Filter dresses by price range
     */
    @GetMapping("/dresses/price-range")
    public ResponseEntity<Map<String, Object>> getDressesByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        List<Dress> dresses = getAllDress.getDressesByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Dresses retrieved by price range",
                "data", dresses,
                "totalCount", dresses.size()
        ));
    }

    /**
     * Get available stock summary
     */
    @GetMapping("/dresses/stock-summary")
    public ResponseEntity<Map<String, Object>> getStockSummary() {
        Map<String, Object> stockSummary = getAllDress.getStockSummary();
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Stock summary retrieved successfully",
                "data", stockSummary
        ));
    }
}
