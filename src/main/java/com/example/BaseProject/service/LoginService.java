package com.example.BaseProject.service;

import com.example.BaseProject.dao.*;
import com.example.BaseProject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {
    @Autowired
    UserDao userDao;

    public UserDto selectUser(Map map) throws Exception {
        return userDao.selectUser(map);
    }
}
