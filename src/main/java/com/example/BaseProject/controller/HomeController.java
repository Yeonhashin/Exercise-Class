package com.example.BaseProject.controller;

import com.example.BaseProject.dao.UserReservationDao;
import com.example.BaseProject.domain.UserReservationDto;
import com.example.BaseProject.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    BoardService boardService;
    @Autowired
    private UserReservationDao userReservationDao;

    @RequestMapping("/")
    public String home(Model m,HttpSession session) throws Exception {
        // "index"는 templates/index.html을 렌더링합니다.
        int userId = (int) session.getAttribute("user_id");

        List<UserReservationDto> reservedClass = userReservationDao.reservedClassByUser(userId);

        System.out.println("reservedClass = " + reservedClass);


        return "index";  // index.html 파일을 반환
    }

    @RequestMapping("/header")
    public String header(Model model) {
        return "layout/header";
    }

    @RequestMapping("/footer")
    public String footer(Model model) {
        return "layout/footer";
    }
}
