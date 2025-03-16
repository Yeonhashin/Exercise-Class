package com.example.BaseProject.dao;

import com.example.BaseProject.domain.ClassTimeDto;
import com.example.BaseProject.domain.ClassTypeDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ClassTimeDao {
    @Autowired
    private SqlSession session;
    private static String namespace="com.example.BaseProject.dao.ClassTimeMapper.";

    public List<ClassTimeDto> selectAll() {
        return session.selectList(namespace + "selectAll");
    }
}
