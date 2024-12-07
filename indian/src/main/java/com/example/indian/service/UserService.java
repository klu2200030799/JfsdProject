package com.example.indian.service;

import com.example.indian.dto.RegistrationDTO;
import com.example.indian.models.User;
import com.example.indian.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    public User createUser(RegistrationDTO registrationDTO) {
        // Check if the email is already used
        if (userRepository.findByEmail(registrationDTO.getEmail()) != null) {
            return null; // User already exists
        }

        // Create a User entity from RegistrationDTO
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword()); // Save only password

        // Save the user to the database
        userRepository.save(user);
        return user;
    }

    public User validateUser(String email, String password) {
        // Assuming User entity has email and password fields
        Optional<User> userOptional = userRepository.findByEmailAndPassword(email, password);
        return userOptional.orElse(null); // Return user if found, otherwise null
    }
}
