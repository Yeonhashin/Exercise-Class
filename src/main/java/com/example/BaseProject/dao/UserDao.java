package com.example.BaseProject.dao;

import com.example.BaseProject.domain.UserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDao {

    @Autowired
    private SqlSession session;
    private static String namespace="com.example.BaseProject.dao.UserMapper.";

    public UserDto selectUser(Map map) throws Exception {
        return session.selectOne(namespace + "select", map);
    }
}
