package com.example.indian.controllers;

import com.example.indian.dto.UserDTO;
import com.example.indian.models.Booking;
import com.example.indian.models.GalleryItem;
import com.example.indian.repositories.GalleryRepository;
import com.example.indian.service.BookingService;
import com.example.indian.service.GalleryService;
import org.springframework.ui.Model;
import com.example.indian.dto.RegistrationDTO;
import com.example.indian.models.User;
import com.example.indian.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class PageController {

    private final UserService userService;
    private final GalleryService galleryService;
    private final BookingService bookingService;

    @GetMapping("/")
    public String index(Model model) {
        List<GalleryItem> monumentsGalleryItems = galleryService.getGalleryItemsByCategory("monuments");
        model.addAttribute("galleryItems", monumentsGalleryItems);
        return "index";
    }

    @GetMapping("/{category}")
    public String getCategoryPage(@PathVariable String category, Model model) {
        List<GalleryItem> categoryGalleryItems = galleryService.getGalleryItemsByCategory(category.equals("arts") ? "art" : category);
        model.addAttribute("galleryItems", categoryGalleryItems);
        return category;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserDTO userDTO, Model model, HttpSession session) {
        User user = userService.validateUser(userDTO.getEmail(), userDTO.getPassword());
        if (user != null) {
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userId", user.getUser_id());
            session.setAttribute("role", user.getRole());
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
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

    @GetMapping("/myBooking/{id}")
    public String myBooking(@PathVariable Integer id, Model model) {
        List<Booking> userBookings = bookingService.getBookingsByUserId(id);
        model.addAttribute("bookings", userBookings);
        return "myBooking";
    }

    @GetMapping("/explore/{id}")
    public String exploreSingleItem(@PathVariable Long id, Model model) {
        GalleryItem galleryItem = galleryService.getGalleryItemById(id);
        model.addAttribute("galleryItem", galleryItem);
        return "explore";
    }

    @GetMapping("/explore")
    public String exploreGallery(Model model) {
        List<GalleryItem> galleryItems = galleryService.getAllGalleryItems();
        model.addAttribute("galleryItems", galleryItems);
        return "explore";
    }
}
