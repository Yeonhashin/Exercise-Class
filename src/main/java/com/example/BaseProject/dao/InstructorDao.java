package com.example.BaseProject.dao;

import com.example.BaseProject.domain.InstructorDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class InstructorDao {
    @Autowired
    private SqlSession session;
    private static String namespace="com.example.BaseProject.dao.InstructorMapper.";

    public List<InstructorDto> selectAll() {
        return session.selectList(namespace + "selectAll");
    }
}
