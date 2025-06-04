package com.example.BaseProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Autowired
    @Qualifier("mailSender")
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendHtmlEmailWithTemplate(String to, String userName, String className, String date) throws MessagingException {
        // HTML 템플릿 렌더링
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("className", className);
        context.setVariable("date", date);
        String htmlContent = templateEngine.process("reservation-email", context); // reservation-email.html

        // 이메일 구성
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setSubject("[필라테스 예약 완료 안내]");
        helper.setText(htmlContent, true); // HTML로 설정

        mailSender.send(message);
    }
}
