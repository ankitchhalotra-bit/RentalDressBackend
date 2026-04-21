package com.custom_dress.demo.gateway.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    private String id;

    private String userId;
    private String dressId;
    private String dressName;
    
    // Rental period
    private Long startDate; // milliseconds
    private Long endDate;
    private Integer rentalDays;
    
    // Pricing
    private Double rentalPrice;
    private Double depositAmount;
    private Double totalAmount;
    
    // Status: PENDING, CONFIRMED, SHIPPED, ACTIVE, RETURN_INITIATED, RETURNED, CANCELLED
    private String status = "PENDING";
    
    // Delivery details
    private String deliveryAddress;
    private String returnAddress;
    
    // Payment
    private String paymentId;
    private String paymentStatus; // PENDING, COMPLETED, FAILED
    
    // Size & Color selected
    private String selectedSize;
    private String selectedColor;
    
    // Metadata
    private Long createdAt = System.currentTimeMillis();
    private Long updatedAt = System.currentTimeMillis();
    private String notes;
}

