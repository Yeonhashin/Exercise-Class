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

    // 수업 예약 new
    public int reserveClass(int userId, int classId, boolean isWait) throws Exception {
        UserReservationDto reservation = new UserReservationDto();
        reservation.setUser_id(userId);
        reservation.setClass_id(classId);

        int result = userReservationDao.insert(reservation);
        if (result > 0) {
            int reservationId = reservation.getId();
            String status = isWait ? "reserve-wait" : "reserve";
            emailAsyncService.triggerEmailAsync(reservationId, status);
        }
        return result;
    }

    // 수업 예약 취소
    public int cancelReservation(int userId, int reservationId, boolean isWait) throws Exception {
        int result = userReservationDao.update(userId, reservationId);
        if (result > 0) {
            String status = isWait ? "cancel-wait" : "cancel";
            emailAsyncService.triggerEmailAsync(reservationId, status);
        }
        return result;
    }

    // 톱 페이지 예약 일람 표시
    public List<UserReservationDto> reservedClassByUser(int userId) throws Exception {
        List<UserReservationDto> reservedList = userReservationDao.reservedClassByUser(userId);
        for (UserReservationDto dto : reservedList) {
            boolean canCancel = ReservationUtils.canCancel(dto.getClass_date(), dto.getClass_start_time());
            dto.setCanCancel(canCancel);
        }
        return reservedList;
    }

    // 예약 일람 표시
    public List<UserReservationDto> reservedAllClassByUser(int userId, int offset, int size) throws Exception {
        List<UserReservationDto> reservedList = userReservationDao.reservedAllClassByUser(userId, offset, size);
        for (UserReservationDto dto : reservedList) {
            boolean canCancel = ReservationUtils.canCancel(dto.getClass_date(), dto.getClass_start_time());
            dto.setCanCancel(canCancel);
        }
        return reservedList;
    }

    // 예약 일람 더보기 표시
    public boolean hasMore(int offset, int userId) throws Exception {
        return userReservationDao.hasMore(offset, userId);
    }
}
