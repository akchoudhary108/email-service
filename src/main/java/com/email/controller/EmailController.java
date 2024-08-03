package com.email.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import com.email.model.EmailRequest;
import com.email.service.EmailService;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        Context context = new Context();
        // Set variables for the template from the POST request data
        context.setVariable("name", emailRequest.getName());
        context.setVariable("message", emailRequest.getMessage());
        context.setVariable("subject", emailRequest.getSubject());
        try {
            emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), "emailTemplate", context);
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}