package com.example.indian.controllers;

import com.example.indian.dto.UserDTO;
import com.example.indian.models.GalleryItem;
import com.example.indian.repositories.GalleryRepository;
import com.example.indian.service.GalleryService;
import org.springframework.ui.Model;
import com.example.indian.dto.RegistrationDTO;
import com.example.indian.models.User;
import com.example.indian.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class PageController {

    private final UserService userService;
    private final GalleryService galleryService;


    private final GalleryRepository galleryItemRepository;

    @GetMapping("/")
    public String index(Model model) {
        // Fetch only "monuments" category gallery items
        List<GalleryItem> monumentsGalleryItems = galleryService.getGalleryItemsByCategory("monuments");
        model.addAttribute("galleryItems", monumentsGalleryItems);
        return "index";
    }


    @GetMapping("/{category}")
    public String getCategoryPage(@PathVariable String category, Model model) {
        // Fetch gallery items for the specific category
        List<GalleryItem> categoryGalleryItems;

        if(category.equals("arts")) {
            categoryGalleryItems = galleryService.getGalleryItemsByCategory("art");
        }
        else {
            categoryGalleryItems = galleryService.getGalleryItemsByCategory(category);
        }
        model.addAttribute("galleryItems", categoryGalleryItems); // Make sure this is added
        return category; // Serve specific category page
    }
//    @GetMapping("/explore/{id}")
//    public String explorePackage(@PathVariable Long id, Model model) {
//        // Fetch the package details by ID
//        GalleryItem packageItem = galleryItemRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid package ID: " + id));
//        model.addAttribute("package", packageItem);
//        return "explore"; // The Thymeleaf template for the explore page
//    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new UserDTO()); // Add a blank UserDTO to the model
        return "login";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserDTO userDTO, Model model, HttpSession session) {
        User user = userService.validateUser(userDTO.getEmail(), userDTO.getPassword());
        if (user != null) {
            // Store username and ID in session
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userId", user.getUser_id());
            session.setAttribute("role", user.getRole());
            return "redirect:/"; // Redirect to home page or dashboard
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login"; // Redirect to login page after logout
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("user", new RegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegistrationDTO registrationDTO, Model model, HttpSession session) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getReenterpassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        User isRegistered = userService.createUser(registrationDTO);
        if (isRegistered != null) {
            session.setAttribute("username", isRegistered.getUsername());
            session.setAttribute("userId", isRegistered.getUser_id());
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Registration failed. Email already in use.");
            return "register";
        }
    }

    @GetMapping("/book")
    public String book() {
        return "book";
    }


    @GetMapping("/explore/{id}")
    public String exploreSingleItem(@PathVariable Long id, Model model) {
        // Fetch the gallery item by ID from the database
        GalleryItem galleryItem = galleryService.getGalleryItemById(id);

        // Add the gallery item to the model
        model.addAttribute("galleryItem", galleryItem);

        // Return the explore page to display this single item
        return "explore";
    }

    // Fetch and display all gallery items (Explore Gallery)
    @GetMapping("/explore")
    public String exploreGallery(Model model) {
        List<GalleryItem> galleryItems = galleryService.getAllGalleryItems();
        model.addAttribute("galleryItems", galleryItems);  // Pass the gallery items to the view
        return "explore";  // Return the explore.html view
    }






}
