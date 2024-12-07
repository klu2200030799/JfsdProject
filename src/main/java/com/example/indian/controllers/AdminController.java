package com.example.indian.controllers;

import com.example.indian.models.Booking;
import com.example.indian.models.GalleryItem;
import com.example.indian.service.BookingService;
import com.example.indian.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final GalleryService galleryService;
    private final BookingService bookingService;

    // Show the Create Post Form
    @GetMapping("/createPost")
    public String createPost() {
        return "createPost"; // This will load the createPost.html form
    }

    // Save the Post with Image URL
    @PostMapping("/save_post")
    public String savePost(@ModelAttribute GalleryItem galleryItem) {
        galleryService.saveGalleryItem(galleryItem);  // Save the gallery item to the database
        return "redirect:/";  // Redirect to the explore page after saving
    }

    @GetMapping("/getBookings")
    public String getBookings(Model model) {
        List<Booking> bookings=bookingService.getAll();
        model.addAttribute("bookings", bookings);
        return "getBookings";
    }


}
