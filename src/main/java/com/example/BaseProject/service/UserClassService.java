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
public class UserClassService {
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

    public List<ClassInfoDto> getClassByDateAndTime(LocalDate class_date, String class_start_time) throws Exception {
        return classInfoDao.getClassByDateAndTime(class_date, class_start_time);
    }

    public List<ClassInfoDto> search(String searchClassDate, String searchClassName, String searchInstructor) throws Exception {
        return classInfoDao.getClassBySearch(searchClassDate, searchClassName, searchInstructor);
    }
}
