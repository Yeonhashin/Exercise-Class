package com.example.BaseProject.dao;

import com.example.BaseProject.domain.NoticeDto;
import com.example.BaseProject.domain.UserDto;
import com.example.BaseProject.domain.UserReservationDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class NoticeDao {

    @Autowired
    private SqlSession session;
    private static String namespace="com.example.BaseProject.dao.NoticeMapper.";

    public List<NoticeDto> selectList(int display_flag) throws Exception {
        return session.selectList(namespace + "select", display_flag);
    }
}
