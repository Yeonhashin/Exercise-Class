package com.example.BaseProject.dao;

import com.example.BaseProject.domain.BoardDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDao {
    @Autowired
    private SqlSession session;
    private static String namespace="com.example.BaseProject.dao.BoardMapper.";

    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    }

    public int insert(BoardDto dto) throws Exception {
        return session.insert(namespace+"insert", dto);
    }

    public List<BoardDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    }

    public List<BoardDto> getWeeklySchedule(LocalDate startDate, LocalDate endDate) throws Exception {
        Map map = new HashMap();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return session.selectList(namespace+"getWeeklySchedule", map);
    }

    public List<BoardDto> getClassByDateAndTime(LocalDate class_date, String class_start_time) throws Exception {
        Map map = new HashMap();
        map.put("class_date", class_date);
        map.put("class_start_time", class_start_time);
        return session.selectList(namespace+"getClassByDateAndTime", map);
    }

    public BoardDto select(Integer id) throws Exception {
        return session.selectOne(namespace + "select", id);
    }

    public List<BoardDto> selectPage(Map map) throws Exception {
        return session.selectList(namespace+"selectPage", map);
    }

    public int update(BoardDto dto) throws Exception {
        return session.update(namespace+"update", dto);
    }

//    @Override
//    public int searchResultCnt(SearchCondition sc) throws Exception {
//        System.out.println("sc in searchResultCnt() = " + sc);
//        System.out.println("session = " + session);
//        return session.selectOne(namespace+"searchResultCnt", sc);
//    }
//
//    @Override
//    public List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception {
//        return session.selectList(namespace+"searchSelectPage", sc);
//    }

    public int deleteAll(){
        return session.delete(namespace + "deleteAll");
    }

    public int delete(Integer id, int class_point_id) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        map.put("class_point_id", class_point_id);
        return session.delete(namespace+"delete", map);
    }
}
