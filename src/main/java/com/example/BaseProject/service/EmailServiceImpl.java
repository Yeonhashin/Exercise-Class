package com.example.BaseProject.service;

import com.example.BaseProject.dao.UserReservationDao;
import com.example.BaseProject.domain.UserReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailServiceImpl self;

    @Autowired
    @Qualifier("mailSender")
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private UserReservationDao userReservationDao;

    @Override
    public void sendHtmlEmailWithTemplate(Integer reservationId, String status) {
        UserReservationDto reservedClass = null;
        try {
            reservedClass = userReservationDao.selectClassById(reservationId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (reservedClass == null) {
            System.err.println("예약 정보가 없습니다. ");
            return;
        }

        // HTML 템플릿 렌더링
        Context context = new Context();
        context.setVariable("userName", reservedClass.getName());
        context.setVariable("className", reservedClass.getClass_name());
        context.setVariable("date", reservedClass.getClass_date());
        context.setVariable("startTime", reservedClass.getClass_start_time());
        context.setVariable("endTime", reservedClass.getClass_end_time());

        String htmlContent = null;
        if (status == "reserve") {
            htmlContent = templateEngine.process("email/reservation-email", context); // reservation-email.html
        } else if (status == "reserve-wait") {
            htmlContent = templateEngine.process("email/wait-reservation-email", context); // wait-reservation-email.html
        } else if (status == "cancel") {
            htmlContent = templateEngine.process("email/cancel-reservation-email", context); // cancel-reservation-email.html
        } else if (status == "cancel-wait") {
            htmlContent = templateEngine.process("email/cancel-wait-reservation-email", context); // cancel-wait-reservation-email.html
        }

        try {
            // 이메일 구성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(reservedClass.getEmail());

            if (status == "reserve") {
                helper.setSubject("[필라테스 예약 완료 안내]");
            } else if (status == "reserve-wait") {
                helper.setSubject("[필라테스 대기 예약 안내]");
            } else if (status == "cancel") {
                helper.setSubject("[필라테스 예약 취소 안내]");
            } else if (status == "cancel-wait") {
                helper.setSubject("[필라테스 대기 예약 취소 안내]");
            }
            helper.setText(htmlContent, true); // HTML로 설정

            mailSender.send(message);
            System.out.println("sendEmail end - " + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("이메일 발송 중 오류 발생");
        }
    }
}
