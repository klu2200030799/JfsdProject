//package com.example.indian.email;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//import java.util.regex.Pattern;
//
//@Service
//public class EmailSenderImpl implements EmailSenderService {
//
//    private final JavaMailSender javaMailSender;
//
//    @Autowired
//    public EmailSenderImpl(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//
//    @Override
//    public void sendEmail(String to, String subject, String content) {
//        if (!isValidEmail(to)) {
//            throw new IllegalArgumentException("Invalid email address: " + to);
//        }
//
//        if (subject == null || subject.trim().isEmpty()) {
//            throw new IllegalArgumentException("Email subject cannot be null or empty");
//        }
//
//        if (content == null || content.trim().isEmpty()) {
//            throw new IllegalArgumentException("Email content cannot be null or empty");
//        }
//
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("bhavagna8@gmail.com");
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(content);
//            javaMailSender.send(message);
//            System.out.println("Email sent successfully to: " + to);
//        } catch (Exception e) {
//            System.err.println("Failed to send email to: " + to);
//            e.printStackTrace();
//            throw new RuntimeException("Error sending email: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * Validates an email address using a regular expression.
//     *
//     * @param email the email address to validate
//     * @return true if the email address is valid, false otherwise
//     */
//    private boolean isValidEmail(String email) {
//        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,6}$";
//        return Pattern.matches(emailRegex, email);
//    }
//}
package com.example.indian.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

public class EmailSenderImpl  implements  EmailSenderService{


    @Autowired
    private final JavaMailSender javaMailSender;

    public EmailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dokuparthykundan@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }
}