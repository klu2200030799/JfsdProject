package com.example.indian.controllers;
import com.example.indian.email.EmailSenderService;
import com.example.indian.models.Booking;
import com.example.indian.models.Email;
import com.example.indian.service.BookingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    private final EmailSenderService emailSenderService;

    // Show the Booking Form
    @GetMapping
    public String showBookingForm() {
        return "book"; // Return the HTML page to show the booking form
    }

    // Handle form submission and save booking
    @PostMapping("/submit")
    public String submitBooking(@ModelAttribute Booking booking, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        booking.setUserId(userId);
        // Save the booking to the database
        Booking savedBooking = bookingService.saveBooking(booking);
        model.addAttribute("booking", savedBooking);

        // Get the user's email from the session
        String userEmail = (String) session.getAttribute("email");
        if (userEmail != null) {
            // Prepare the email content
            String emailSubject = "Booking Confirmation";
            String emailBody = String.format(
                    "Dear Customer,\n\nThank you for your booking!\n\nDetails:\n" +
                            "Booking ID: %d\nLocation: %s\nNumber of Persons: %d\n" +
                            "Phone Number: %s\nArrival Date: %s\nDeparture Date: %s\n\n" +
                            "We look forward to hosting you!\n\nBest regards,\nYour Company",
                    savedBooking.getId(),
                    savedBooking.getLocation(),
                    savedBooking.getNumberOfPersons(),
                    savedBooking.getPhoneNumber(),
                    savedBooking.getArrivalDate(),
                    savedBooking.getDepartureDate()
            );

            // Send the email
            emailSenderService.sendEmail(userEmail, emailSubject, emailBody);
        } else {
            System.err.println("User email not found in session.");
        }

        // Return a confirmation page
        return "bookingConfirmation";
    }



//    @PostMapping("/send-email")
//    public ResponseEntity sendEmail(@RequestBody Email email){
//        emailSenderService.sendEmail(email.getTo(),email.getSubject(), email.getBody());
//        return ResponseEntity.ok("Success");
//    }
}
