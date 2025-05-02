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
    private static String namespace="com.example.BaseProject.dao.ClassInfoMapper.";

//    public List<ClassInfoDto> selectAll() throws Exception {
//        return session.selectList(namespace+"selectAll");
//    }

//    public List<ClassInfoDto> getWeeklySchedule(LocalDate startDate, LocalDate endDate) throws Exception {
//        Map map = new HashMap();
//        map.put("startDate", startDate);
//        map.put("endDate", endDate);
//        return session.selectList(namespace+"getWeeklySchedule", map);
//    }

    public List<ClassInfoDto> getClassByDateAndTime(LocalDate class_date, String class_start_time) throws Exception {
        Map map = new HashMap();
        map.put("class_date", class_date);
        map.put("class_start_time", class_start_time);
        return session.selectList(namespace+"getClassByDateAndTime", map);
    }

    public ClassInfoDto select(Integer id) throws Exception {
        return session.selectOne(namespace + "select", id);
    }

//    public List<ClassInfoDto> selectPage(Map map) throws Exception {
//        return session.selectList(namespace+"selectPage", map);
//    }
//
//    public int update(ClassInfoDto dto) throws Exception {
//        return session.update(namespace+"update", dto);
//    }
//
//    public int deleteAll(){
//        return session.delete(namespace + "deleteAll");
//    }

//    public int delete(Integer id, int class_point_id) throws Exception {
//        Map map = new HashMap();
//        map.put("id", id);
//        map.put("class_point_id", class_point_id);
//        return session.delete(namespace+"delete", map);
//    }

    public List<ClassInfoDto> getClassBySearch(String searchClassDate, String searchClassName, String searchInstructor) {
        Map map = new HashMap();
        map.put("searchClassDate", searchClassDate);
        map.put("searchClassName", searchClassName);
        map.put("searchInstructor", searchInstructor);
        return session.selectList(namespace+"getSearchData", map);
    }
}
