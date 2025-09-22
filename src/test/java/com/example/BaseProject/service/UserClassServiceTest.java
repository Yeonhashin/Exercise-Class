package com.example.BaseProject.service;

import com.example.BaseProject.dao.ClassInfoDao;
import com.example.BaseProject.domain.ClassInfoDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserClassServiceTest {

    @InjectMocks
    private UserClassService userClassService;

    @Mock
    private ClassInfoDao classInfoDao;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetScheduleMap() throws Exception {
        int userId = 1;
        LocalDate startDate = LocalDate.of(2025, 9, 22);
        int numberOfDays = 2;

        // DAO에서 반환할 모의 데이터 생성
        ClassInfoDto dto1 = new ClassInfoDto();
        dto1.setClass_date("2025-09-22");
        dto1.setClass_start_time("10:00");
        dto1.setVacancy(5);

        ClassInfoDto dto2 = new ClassInfoDto();
        dto2.setClass_date("2025-09-23");
        dto2.setClass_start_time("10:00");
        dto2.setVacancy(0); // 대기 상태 테스트용

        // DAO 목 동작 정의
        when(classInfoDao.getClassByDateAndTime(eq(startDate), anyString(), eq(userId)))
                .thenReturn(List.of(dto1));
        when(classInfoDao.getClassByDateAndTime(eq(startDate.plusDays(1)), anyString(), eq(userId)))
                .thenReturn(List.of(dto2));

        // 테스트 대상 호출
        Map<String, Map<String, List<ClassInfoDto>>> scheduleMap =
                userClassService.getScheduleMap(startDate, numberOfDays, userId);

        // 결과 검증
        assertNotNull(scheduleMap);
        assertTrue(scheduleMap.containsKey("10:00"));

        Map<String, List<ClassInfoDto>> dailyMap = scheduleMap.get("10:00");
        assertEquals(2, dailyMap.size());

        List<ClassInfoDto> firstDayClasses = dailyMap.get("9/22(월)");
        assertEquals(1, firstDayClasses.size());
        assertFalse(firstDayClasses.get(0).isHas_waiting());

        List<ClassInfoDto> secondDayClasses = dailyMap.get("9/23(화)");
        assertEquals(1, secondDayClasses.size());
        assertTrue(secondDayClasses.get(0).isHas_waiting());

        // DAO 호출 여부 검증
        verify(classInfoDao, times(7)).getClassByDateAndTime(eq(startDate), anyString(), eq(userId));
        verify(classInfoDao, times(7)).getClassByDateAndTime(eq(startDate.plusDays(1)), anyString(), eq(userId));
    }
}
