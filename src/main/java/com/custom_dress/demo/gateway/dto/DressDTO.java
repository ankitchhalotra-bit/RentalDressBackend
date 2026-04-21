package com.custom_dress.demo.gateway.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DressDTO {
    private String id;
    private String name;
    private String description;
    private String categoryId;
    private Double rentalPricePerDay;
    private Double depositAmount;
    private java.util.List<String> imageUrls;
    private java.util.List<String> sizes;
    private java.util.List<String> colors;
    private Integer totalStock;
    private Integer availableStock;
    private String condition;
    private String occasion;
    private String material;
    private Double averageRating;
    private Integer totalReviews;
    private Boolean active;
}

