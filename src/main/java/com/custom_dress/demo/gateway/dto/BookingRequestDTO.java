package com.custom_dress.demo.gateway.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {
    private String dressId;
    private Long startDate;
    private Long endDate;
    private String selectedSize;
    private String selectedColor;
    private String deliveryAddress;
    private String returnAddress;
    private String notes;
}

