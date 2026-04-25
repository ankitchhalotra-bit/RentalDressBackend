package com.custom_dress.demo.gateway.service.admin;

import com.custom_dress.demo.gateway.model.Dress;
import com.custom_dress.demo.gateway.repository.DressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetAllDressService {

    @Autowired
    DressRepository dressRepository;

    /**
     * Get all dresses including inactive ones (Admin use)
     */
    public List<Dress> getAllDressesData(){
        return dressRepository.findAll();
    }

    /**
     * Get only active dresses (for users/customers)
     */
    public List<Dress> getAllActiveDresses(){
        return dressRepository.findByActiveTrue();
    }

    /**
     * Get dresses with pagination and optional filtering
     */
    public Map<String, Object> getDressesWithPagination(Integer page, Integer size, String occasion, String category){
        List<Dress> allDresses = getAllActiveDresses();
        
        // Apply filters
        if (occasion != null && !occasion.isEmpty()) {
            allDresses = allDresses.stream()
                    .filter(d -> d.getOccasion() != null && d.getOccasion().equalsIgnoreCase(occasion))
                    .collect(Collectors.toList());
        }
        
        if (category != null && !category.isEmpty()) {
            allDresses = allDresses.stream()
                    .filter(d -> d.getCategoryId() != null && d.getCategoryId().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }
        
        // Apply pagination
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, allDresses.size());
        List<Dress> paginatedDresses = allDresses.subList(startIndex, endIndex);
        
        Map<String, Object> result = new HashMap<>();
        result.put("content", paginatedDresses);
        result.put("totalElements", allDresses.size());
        result.put("totalPages", (int) Math.ceil((double) allDresses.size() / size));
        result.put("currentPage", page);
        result.put("pageSize", size);
        result.put("hasNext", endIndex < allDresses.size());
        result.put("hasPrevious", page > 0);
        
        return result;
    }

    /**
     * Get statistics about dresses (total, active, stock, etc.)
     */
    public Map<String, Object> getDressesStatistics(){
        List<Dress> allDresses = getAllDressesData();
        List<Dress> activeDresses = getAllActiveDresses();
        
        int totalDresses = allDresses.size();
        int activeDressCount = activeDresses.size();
        int inactiveDressCount = totalDresses - activeDressCount;
        
        long totalStock = activeDresses.stream().mapToLong(d -> d.getTotalStock() != null ? d.getTotalStock() : 0).sum();
        long availableStock = activeDresses.stream().mapToLong(d -> d.getAvailableStock() != null ? d.getAvailableStock() : 0).sum();
        long bookedStock = totalStock - availableStock;
        
        double avgPrice = activeDresses.stream()
                .mapToDouble(d -> d.getRentalPricePerDay() != null ? d.getRentalPricePerDay() : 0)
                .average().orElse(0.0);
        
        Optional<Dress> mostExpensive = activeDresses.stream()
                .max(Comparator.comparing(d -> d.getRentalPricePerDay() != null ? d.getRentalPricePerDay() : 0));
        
        Optional<Dress> cheapest = activeDresses.stream()
                .min(Comparator.comparing(d -> d.getRentalPricePerDay() != null ? d.getRentalPricePerDay() : 0));
        
        // Group by occasion
        Map<String, Long> dressesByOccasion = activeDresses.stream()
                .filter(d -> d.getOccasion() != null)
                .collect(Collectors.groupingBy(Dress::getOccasion, Collectors.counting()));
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDresses", totalDresses);
        stats.put("activeDresses", activeDressCount);
        stats.put("inactiveDresses", inactiveDressCount);
        stats.put("totalStock", totalStock);
        stats.put("availableStock", availableStock);
        stats.put("bookedStock", bookedStock);
        stats.put("averageRentalPrice", String.format("%.2f", avgPrice));
        stats.put("mostExpensive", mostExpensive.map(d -> Map.of(
                "name", d.getName(),
                "price", d.getRentalPricePerDay()
        )).orElse(null));
        stats.put("cheapest", cheapest.map(d -> Map.of(
                "name", d.getName(),
                "price", d.getRentalPricePerDay()
        )).orElse(null));
        stats.put("dressesByOccasion", dressesByOccasion);
        
        return stats;
    }

    /**
     * Search dresses by name or description
     */
    public List<Dress> searchDresses(String query){
        return dressRepository.searchByNameOrDescription(query);
    }

    /**
     * Get dresses by category
     */
    public List<Dress> getDressesByCategory(String categoryId){
        return dressRepository.findByCategoryId(categoryId);
    }

    /**
     * Get dresses by occasion
     */
    public List<Dress> getDressesByOccasion(String occasion){
        return dressRepository.findByOccasion(occasion);
    }

    /**
     * Get dresses within price range
     */
    public List<Dress> getDressesByPriceRange(Double minPrice, Double maxPrice){
        return dressRepository.findByPriceRange(minPrice, maxPrice);
    }

    /**
     * Get stock summary for all dresses
     */
    public Map<String, Object> getStockSummary(){
        List<Dress> activeDresses = getAllActiveDresses();
        
        Map<String, Object> lowStockItems = new HashMap<>();
        List<Map<String, ?>> lowStockList = activeDresses.stream()
                .filter(d -> d.getAvailableStock() != null && d.getAvailableStock() < 5)
                .map(d -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("id", d.getId());
                    item.put("name", d.getName());
                    item.put("availableStock", d.getAvailableStock());
                    item.put("totalStock", d.getTotalStock());
                    return item;
                })
                .collect(Collectors.toList());
        
        List<Map<String, ?>> outOfStockList = activeDresses.stream()
                .filter(d -> d.getAvailableStock() != null && d.getAvailableStock() == 0)
                .map(d -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("id", d.getId());
                    item.put("name", d.getName());
                    item.put("availableStock", d.getAvailableStock());
                    item.put("totalStock", d.getTotalStock());
                    return item;
                })
                .collect(Collectors.toList());
        
        Map<String, Object> stockSummary = new HashMap<>();
        stockSummary.put("totalActiveDresses", activeDresses.size());
        stockSummary.put("totalStock", activeDresses.stream()
                .mapToLong(d -> d.getTotalStock() != null ? d.getTotalStock() : 0).sum());
        stockSummary.put("totalAvailableStock", activeDresses.stream()
                .mapToLong(d -> d.getAvailableStock() != null ? d.getAvailableStock() : 0).sum());
        stockSummary.put("lowStockCount", lowStockList.size());
        stockSummary.put("outOfStockCount", outOfStockList.size());
        stockSummary.put("lowStockItems", lowStockList);
        stockSummary.put("outOfStockItems", outOfStockList);
        
        return stockSummary;
    }
}
