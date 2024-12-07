package com.example.indian.controllers;
import com.example.indian.models.Booking;
import com.example.indian.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // Show the Booking Form
    @GetMapping
    public String showBookingForm() {
        return "book"; // Return the HTML page to show the booking form
    }

    // Handle form submission and save booking
    @PostMapping("/submit")
    public String submitBooking(@ModelAttribute Booking booking, Model model) {
        Booking savedBooking = bookingService.saveBooking(booking); // Save the booking to the database
        model.addAttribute("booking", savedBooking); // Add the saved booking to the model
        return "bookingConfirmation"; // Return a confirmation page
    }
}
