package com.example.BaseProject.dao;

import com.example.BaseProject.domain.BoardDto;
import com.example.BaseProject.domain.UserReservationDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserReservationDao {

    @Autowired
    private SqlSession session;
    private static String namespace="com.example.BaseProject.dao.UserReservationMapper.";

    public int insert(UserReservationDto dto) throws Exception {
        return session.insert(namespace+"insert", dto);
    }

    public int update(int userId, int classId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("class_id", classId);
        return session.update(namespace+"update", map);
    }

    public List<Integer> findReservedClassIdsByUser(int userId) {
        return session.selectList(namespace + "selectReservedClassIdsByUser", userId);
    }

    public List<UserReservationDto> reservedClassByUser(int userId) throws Exception {
        return session.selectList(namespace+"selectReservedClassByUser", userId);
    }

    public List<UserReservationDto> reservedAllClassByUser(int userId) throws Exception {
        return session.selectList(namespace+"selectAllReservedClassByUser", userId);
    }



}
