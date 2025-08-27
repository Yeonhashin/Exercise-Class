package com.example.BaseProject.init;

import com.example.BaseProject.dao.ClassInfoDao;
import com.example.BaseProject.domain.ClassInfoDto;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
public class TestDataInitializer implements InitializingBean {

    private final ClassInfoDao classInfoDao;

    public TestDataInitializer(ClassInfoDao classInfoDao) {
        this.classInfoDao = classInfoDao;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LocalDate today = LocalDate.now();
        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            LocalDate classDate = today.plusDays(i);

            if (classInfoDao.getClassByDate(classDate).isEmpty()) {

                // 수업 시간 1~7 반복
                for (int timeId = 1; timeId <= 7; timeId++) {
                    ClassInfoDto classInfo = new ClassInfoDto();
                    classInfo.setClass_type_id(rand.nextInt(4) + 1); // 1~4 랜덤
                    classInfo.setClass_time_id(timeId);              // 시간 ID 1~7
                    classInfo.setClass_point_id(1);                 // 고정
                    classInfo.setInstructor_id(rand.nextInt(3) + 1);// 1~3 랜덤
                    classInfo.setClass_date(String.valueOf(classDate));
                    classInfo.setMaximum(10);

                    classInfoDao.insertClassInfo(classInfo);
                }

                System.out.println("테스트 데이터 삽입 완료: " + classDate);
            }
        }
    }
}
