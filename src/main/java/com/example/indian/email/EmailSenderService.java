package com.example.indian.email;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String content);
}
