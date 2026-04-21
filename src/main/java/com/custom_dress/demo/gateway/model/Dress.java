package com.custom_dress.demo.gateway.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "dresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dress {

    @Id
    private String id;

    private String name;
    private String description;
    private String categoryId;
    
    // Pricing
    private Double rentalPricePerDay;
    private Double depositAmount;
    
    // Images
    private List<String> imageUrls; // Cloudinary URLs
    
    // Sizing & Colors
    private List<String> sizes; // XS, S, M, L, XL, XXL
    private List<String> colors;
    
    // Stock management
    private Integer totalStock;
    private Integer availableStock;
    
    // Condition & Details
    private String condition; // EXCELLENT, GOOD, FAIR
    private String occasion; // WEDDING, PARTY, CASUAL, FORMAL
    private String material; // SILK, COTTON, BLEND, etc.
    
    // Ratings
    private Double averageRating;
    private Integer totalReviews;
    
    // Metadata
    private Boolean active = true;
    private Long createdAt = System.currentTimeMillis();
    private Long updatedAt = System.currentTimeMillis();
}

