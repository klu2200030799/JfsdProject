package com.example.indian.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GalleryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String image;
    private String title;
    @Lob
    private String description;

    private String category;

    // Getters and setters
}
