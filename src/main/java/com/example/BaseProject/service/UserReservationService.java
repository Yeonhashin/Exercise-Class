package com.example.BaseProject.service;

import com.example.BaseProject.dao.*;
import com.example.BaseProject.domain.ClassInfoDto;
import com.example.BaseProject.domain.UserReservationDto;
import com.example.BaseProject.util.ReservationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public int reserveClass(UserReservationDto userReservationDto) throws Exception {
        return userReservationDao.insert(userReservationDto);
    }

    public int cancelReservation(int userId, int classId) throws Exception {
        return userReservationDao.update(userId, classId);
    }

    public List<UserReservationDto> reservedAllClassByUser(int userId, int offset, int size) throws Exception {
        List<UserReservationDto> reservedList = userReservationDao.reservedAllClassByUser(userId, offset, size);
        for (UserReservationDto dto : reservedList) {
            boolean canCancel = ReservationUtils.canCancel(dto.getClass_date(), dto.getClass_start_time());
            dto.setCanCancel(canCancel);
        }
        return reservedList;
    }

    public boolean hasMore(int userId, int offset) throws Exception {
        return userReservationDao.hasMore(userId, offset);
    }
}
