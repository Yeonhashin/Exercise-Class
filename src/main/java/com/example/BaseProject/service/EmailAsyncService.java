package com.example.BaseProject.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailAsyncService {

    public final EmailService emailService;

    public EmailAsyncService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    public void triggerEmailAsync(int reservationId, String status) {
        emailService.sendHtmlEmailWithTemplate(reservationId, status);
    }
}
