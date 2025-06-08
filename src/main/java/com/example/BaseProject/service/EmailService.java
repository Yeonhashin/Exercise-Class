package com.example.BaseProject.service;

public interface EmailService {
    void sendHtmlEmailWithTemplate(Integer reservationId, String status);
}
