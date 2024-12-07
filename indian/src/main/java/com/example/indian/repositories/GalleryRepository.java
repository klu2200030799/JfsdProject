package com.example.indian.repositories;


import com.example.indian.models.GalleryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<GalleryItem, Long> {
    List<GalleryItem> findByCategory(String category);
}

