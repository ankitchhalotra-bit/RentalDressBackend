package com.custom_dress.demo.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dress")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddDressInfoDTO {

    @Id
    private String id;

    private String dressID;   // MongoDB usually uses String or ObjectId

    private String name;
    private String type;
    private Double price;
    private String description;

    private String file;    // Cloudinary URL
    private String publicId;

    private boolean favorite;
    private boolean addCart;

    // getters and setters
}
