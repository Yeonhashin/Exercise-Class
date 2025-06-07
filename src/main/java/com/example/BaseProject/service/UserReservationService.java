package com.example.BaseProject.service;

import com.example.BaseProject.dao.*;
import com.example.BaseProject.domain.UserReservationDto;
import com.example.BaseProject.util.ReservationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserReservationService {
    @Autowired
    ClassInfoDao classInfoDao;
    @Autowired
    InstructorDao instructorDao;
    @Autowired
    ClassPointDao classPointDao;
    @Autowired
    ClassTypeDao classTypeDao;
    @Autowired
    ClassTimeDao classTimeDao;
    @Autowired
    UserReservationDao userReservationDao;

    @Autowired
    @Lazy
    private EmailAsyncService emailAsyncService;

    public UserReservationService(EmailAsyncService emailAsyncService) {
        this.emailAsyncService = emailAsyncService;
    }

    @Autowired
    private ApplicationContext context;

    public int reserveClass(int userId, int classId) throws Exception {
        UserReservationDto reservation = new UserReservationDto();
        reservation.setUser_id(userId);
        reservation.setClass_id(classId);

        int result = userReservationDao.insert(reservation);

        if (result > 0) {
            int reservationId = reservation.getId();
            System.out.println("main thread - " + Thread.currentThread().getName());
            String status = "reservate";
            //emailServiceImpl.sendHtmlEmailWithTemplate(reservationId, status);

            // emailAsyncService.triggerEmailAsync(reservationId, status);
            context.getBean(EmailAsyncService.class).triggerEmailAsync(reservationId, status);
        }
        return result;
    }

    public int cancelReservation(int userId, int classId) throws Exception {
        return userReservationDao.update(userId, classId);
    }

    public List<UserReservationDto> reservedClassByUser(int userId) throws Exception {
        List<UserReservationDto> reservedList = userReservationDao.reservedClassByUser(userId);
        for (UserReservationDto dto : reservedList) {
            boolean canCancel = ReservationUtils.canCancel(dto.getClass_date(), dto.getClass_start_time());
            dto.setCanCancel(canCancel);
        }
        return reservedList;
    }

    public List<UserReservationDto> reservedAllClassByUser(int userId, int offset, int size) throws Exception {
        List<UserReservationDto> reservedList = userReservationDao.reservedAllClassByUser(userId, offset, size);
        for (UserReservationDto dto : reservedList) {
            boolean canCancel = ReservationUtils.canCancel(dto.getClass_date(), dto.getClass_start_time());
            dto.setCanCancel(canCancel);
        }
        return reservedList;
    }

    public boolean hasMore(int offset, int userId) throws Exception {
        return userReservationDao.hasMore(offset, userId);
    }
}
