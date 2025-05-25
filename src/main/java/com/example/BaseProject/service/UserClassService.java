package com.example.BaseProject.service;

import com.example.BaseProject.dao.*;
import com.example.BaseProject.domain.ClassInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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

    private static final List<String> CLASS_TIMES = List.of("10:00", "11:30", "13:00", "15:30", "17:00", "19:30", "21:00");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("M/d(E)");

    public List<ClassInfoDto> getClassByDateAndTime(LocalDate class_date, String class_start_time) throws Exception {
        return classInfoDao.getClassByDateAndTime(class_date, class_start_time);
    }

    public List<ClassInfoDto> search(String searchClassDate, String searchClassName, String searchInstructor, int offset, int size) throws Exception {
        return classInfoDao.getClassBySearch(searchClassDate, searchClassName, searchInstructor, offset, size);
    }

    public boolean hasMore(int offset, String searchClassDate, String searchClassName, String searchInstructor) throws Exception {
        return classInfoDao.hasMore(offset, searchClassDate, searchClassName, searchInstructor);
    }

    public Map<String, Map<String, List<ClassInfoDto>>> getScheduleMap(LocalDate startDate, int numberOfDays) throws Exception {
        Map<String, Map<String, List<ClassInfoDto>>> scheduleMap = new TreeMap<>();

        for (String time : CLASS_TIMES) {
            Map<String, List<ClassInfoDto>> dailyMap = new TreeMap<>();

            for (int i = 0; i < numberOfDays; i++) {
                LocalDate date = startDate.plusDays(i);
                String formattedDate = date.format(FORMATTER);

                List<ClassInfoDto> classList = getClassByDateAndTime(date, time);
                dailyMap.put(formattedDate, classList);
            }

            scheduleMap.put(time, dailyMap);
        }

        return scheduleMap;
    }

    // ✅ 추가된 메서드 2: 날짜 문자열 목록 생성
    public List<String> getFormattedDates(LocalDate startDate, int numberOfDays) {
        return IntStream.range(0, numberOfDays)
                .mapToObj(i -> startDate.plusDays(i).format(FORMATTER))
                .collect(Collectors.toList());
    }

    // ✅ 추가된 메서드 3: 시간대 목록 반환
    public List<String> getClassTimes() {
        return CLASS_TIMES;
    }

}
