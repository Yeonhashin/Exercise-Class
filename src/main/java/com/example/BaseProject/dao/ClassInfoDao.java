package com.example.BaseProject.dao;

import com.example.BaseProject.domain.ClassInfoDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClassInfoDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "com.example.BaseProject.dao.ClassInfoMapper.";

    public List<ClassInfoDto> getClassByDateAndTime(LocalDate class_date, String class_start_time, int userId) throws Exception {
        Map map = new HashMap();
        map.put("class_date", class_date);
        map.put("class_start_time", class_start_time);
        map.put("userId", userId);
        return session.selectList(namespace + "getClassByDateAndTime", map);
    }

    public ClassInfoDto select(Integer id) throws Exception {
        return session.selectOne(namespace + "select", id);
    }

    public List<ClassInfoDto> getClassBySearch(String searchClassDate, String searchClassName, String searchInstructor, int offset, int size, int userId) {
        Map map = new HashMap();
        map.put("searchClassDate", searchClassDate);
        map.put("searchClassName", searchClassName);
        map.put("searchInstructor", searchInstructor);
        map.put("offset", offset);
        map.put("size", size);
        map.put("userId", userId);
        return session.selectList(namespace + "getSearchData", map);
    }

    public boolean hasMore(int offset, String searchClassDate, String searchClassName, String searchInstructor) {

        Map map = new HashMap();
        map.put("searchClassDate", searchClassDate);
        map.put("searchClassName", searchClassName);
        map.put("searchInstructor", searchInstructor);
        map.put("offset", offset);
        Integer result = session.selectOne(namespace + "hasMore", map);
        return result != null && result > 0;
    }

    public int updateVacancyPlus(Integer classId) throws Exception {
        return session.update(namespace + "updateVacancyPlus", classId);
    }

    public int updateVacancyMinus(Integer classId) throws Exception {
        return session.update(namespace + "updateVacancyMinus", classId);
    }


    public int countClassInfo() {
        return session.selectOne(namespace + "countClassInfo");
    }

    public void insertClassInfo(ClassInfoDto classInfoDto) {
        session.insert(namespace + "insertClassInfo", classInfoDto);
    }

    public List<ClassInfoDto> getClassByDate(LocalDate classDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("class_date", classDate);
        return session.selectList(namespace + "getClassByDate", map);
    }
}
