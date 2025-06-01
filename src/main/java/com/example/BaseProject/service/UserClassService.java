package com.example.BaseProject.service;

import com.example.BaseProject.dao.*;
import com.example.BaseProject.domain.ClassInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    // getClassTimes :: 수업시간 출력
    public List<String> getClassTimes() {
        return CLASS_TIMES;
    }

    // search :: 검색내용에 대해 수업 일람 출력
    public List<ClassInfoDto> search(String searchClassDate, String searchClassName, String searchInstructor, int offset, int size, int userId) throws Exception {
        return classInfoDao.getClassBySearch(searchClassDate, searchClassName, searchInstructor, offset, size, userId);
    }

    // hasMore :: 더보기 버튼 표시용
    public boolean hasMore(int offset, String searchClassDate, String searchClassName, String searchInstructor) throws Exception {
        return classInfoDao.hasMore(offset, searchClassDate, searchClassName, searchInstructor);
    }

    // getDatesInRange :: 5일치의 날짜 출력
    public List<LocalDate> getDatesInRange(LocalDate startDate, int numberOfDays) {
        return IntStream.range(0, numberOfDays)
                .mapToObj(startDate::plusDays)
                .collect(Collectors.toList());
    }

    // getFormattedDates :: startDate 로부터 numberOfDays 까지의 날짜 포맷팅
    public List<String> getFormattedDates(LocalDate startDate, int numberOfDays) {
        return getDatesInRange(startDate, numberOfDays).stream()
                .map(this::formatDate)
                .collect(Collectors.toList());
    }

    // getScheduleMap :: 검색용 수업일람
    public Map<String, Map<String, List<ClassInfoDto>>> getScheduleMap(LocalDate startDate, int numberOfDays, int userId) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Map<String, Map<String, List<ClassInfoDto>>> scheduleMap = new TreeMap<>();

        for (String time : CLASS_TIMES) {
            Map<String, List<ClassInfoDto>> dailyMap = new TreeMap<>();
            for (LocalDate date : getDatesInRange(startDate, numberOfDays)) {
                String formattedDate = formatDate(date);
                List<ClassInfoDto> classes = fetchAndMarkPastClasses(date, time, userId, now);
                dailyMap.put(formattedDate, classes);
            }
            scheduleMap.put(time, dailyMap);
        }
        return scheduleMap;
    }

    private List<ClassInfoDto> fetchAndMarkPastClasses(LocalDate date, String time, int userId, LocalDateTime now) throws Exception {
        List<ClassInfoDto> classList = classInfoDao.getClassByDateAndTime(date, time, userId);
        for (ClassInfoDto dto : classList) {
            try {
                LocalDate classDate = LocalDate.parse(dto.getClass_date());
                LocalTime classEndTime = LocalTime.parse(dto.getClass_end_time());
                dto.setPast(LocalDateTime.of(classDate, classEndTime).isBefore(now));
            } catch (Exception e) {
                dto.setPast(false);
            }
        }
        return classList;
    }

    private String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
}
