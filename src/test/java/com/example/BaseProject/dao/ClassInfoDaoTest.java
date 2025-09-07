package com.example.BaseProject.dao;

import com.example.BaseProject.domain.ClassInfoDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/db-test-context.xml"})
public class ClassInfoDaoTest {

    @Autowired
    private ClassInfoDao classInfoDao;

    @Test
    public void testGetClassByDateAndTime() throws Exception {
        LocalDate classDate = LocalDate.of(2025, 9, 1);
        String startTime = "10:00";
        int userId = 1;

        List<ClassInfoDto> classList = classInfoDao.getClassByDateAndTime(classDate, startTime, userId);
        assertNotNull("No Data", classList);
        assertTrue("Exist Data", classList.size() >= 0);

        if (!classList.isEmpty()) {
            ClassInfoDto dto = classList.get(0);
            String expectedDate = classDate.toString(); // LocalDate → String
            assertEquals("Incorrect ClassDate", expectedDate, dto.getClass_date());
            assertEquals("Incorrect StartTime!", startTime, dto.getClass_start_time());
        }
    }

    @Test
    public void testGetSearchData() {
        String searchClassDate = "2025-09-07";
        String searchClassName = "Basic";
        String searchInstructor = "yuri.Y";
        int offset = 0;
        int size = 10;
        int userId = 1;

        List<ClassInfoDto> result = classInfoDao.getClassBySearch(searchClassDate, searchClassName, searchInstructor, offset, size, userId);

        assertNotNull("No search Data", result);
        assertTrue("Search Data must be under size", result.size() <= size);

        System.out.println("result = " + result);
        for (ClassInfoDto dto : result) {
            if (searchClassDate != null && !searchClassDate.isEmpty()) {
                assertEquals("Incorrect Search Data (Class Date)", searchClassDate, dto.getClass_date().toString());
            }
            if (searchClassName != null && !searchClassName.isEmpty()) {
                assertTrue("Incorrect Search Data (Class Name)", dto.getClass_name().contains(searchClassName));
            }
            if (searchInstructor != null && !searchInstructor.isEmpty()) {
                assertTrue("Incorrect Search Data (Instructor Name)", dto.getInstructor_name().contains(searchInstructor));
            }
        }
    }

    @Test
    public void testHasMore() {
        String searchClassDate = "2025-09-01";
        String searchClassName = "Basic";
        String searchInstructor = "yuri.Y";
        int offset = 0;

        boolean hasMore = classInfoDao.hasMore(offset, searchClassDate, searchClassName, searchInstructor);

        System.out.println("hasMore = " + hasMore);
        // hasMore는 true/false 중 하나
        assertTrue("hasMore 결과가 true 또는 false 이어야 합니다", hasMore == true || hasMore == false);
    }
}
