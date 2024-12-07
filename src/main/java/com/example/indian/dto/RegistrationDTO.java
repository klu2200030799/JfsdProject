package com.example.indian.dto;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String username;
    private String email;
    private String password;
    private String reenterpassword;
}