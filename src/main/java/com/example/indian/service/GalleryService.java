package com.example.indian.service;

import com.example.indian.models.GalleryItem;
import com.example.indian.repositories.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class GalleryService {


    private final GalleryRepository galleryRepository;

    // Save a gallery item to the database
    public void saveGalleryItem(GalleryItem galleryItem) {
        galleryRepository.save(galleryItem);
    }

    // Get all gallery items from the database
    public List<GalleryItem> getAllGalleryItems() {
        return galleryRepository.findAll();
    }

    public GalleryItem getGalleryItemById(Long id) {
        return galleryRepository.findById(id).orElse(null);
    }
    public List<GalleryItem> getGalleryItemsByCategory(String category) {
        return galleryRepository.findByCategory(category);
    }
}
