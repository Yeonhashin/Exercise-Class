package com.example.BaseProject.dao;

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
    private static String namespace = "com.example.BaseProject.dao.UserReservationMapper.";

    public int insert(UserReservationDto reservation) throws Exception {
        return session.insert(namespace + "insert", reservation);
    }

    public int update(int userId, int reservationId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("id", reservationId);
        return session.update(namespace + "update", map);
    }

    public UserReservationDto selectClassById(int reservationId) throws Exception {
        return session.selectOne(namespace + "selectClassById", reservationId);
    }

    public List<UserReservationDto> reservedClassByUser(int userId) throws Exception {
        return session.selectList(namespace + "selectReservedClassByUser", userId);
    }

    public List<UserReservationDto> reservedAllClassByUser(int userId, int offset, int size) throws Exception {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("offset", offset);
        map.put("size", size);
        return session.selectList(namespace + "selectAllReservedClassByUser", map);
    }

    public boolean hasMore(int offset, int user_id) {

        Map map = new HashMap();
        map.put("offset", offset);
        map.put("user_id", user_id);
        Integer result = session.selectOne(namespace + "hasMore", map);
        return result != null && result > 0;
    }

}
