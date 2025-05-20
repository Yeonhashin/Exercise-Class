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

    public List<ClassInfoDto> getClassByDateAndTime(LocalDate class_date, String class_start_time) throws Exception {
        Map map = new HashMap();
        map.put("class_date", class_date);
        map.put("class_start_time", class_start_time);
        return session.selectList(namespace + "getClassByDateAndTime", map);
    }

    public ClassInfoDto select(Integer id) throws Exception {
        return session.selectOne(namespace + "select", id);
    }

    public List<ClassInfoDto> getClassBySearch(String searchClassDate, String searchClassName, String searchInstructor, int offset, int size) {
        Map map = new HashMap();
        map.put("searchClassDate", searchClassDate);
        map.put("searchClassName", searchClassName);
        map.put("searchInstructor", searchInstructor);
        map.put("offset", offset);
        map.put("size", size);
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
}
