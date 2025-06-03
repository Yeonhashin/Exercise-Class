package com.example.BaseProject.controller;

import com.example.BaseProject.dao.NoticeDao;
import com.example.BaseProject.dao.UserReservationDao;
import com.example.BaseProject.domain.NoticeDto;
import com.example.BaseProject.domain.UserReservationDto;
import com.example.BaseProject.service.UserReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserReservationDao userReservationDao;

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    UserReservationService userReservationService;

    @RequestMapping("/")
    public String home(Model m, HttpSession session) throws Exception {
        // "index"는 templates/index.html을 렌더링합니다.
        int userId = (int) session.getAttribute("user_id");

        List<UserReservationDto> reservedClass = userReservationService.reservedClassByUser(userId);
        m.addAttribute("reservedClass", reservedClass);
        
        return "index";  // index.html 파일을 반환
    }

    @GetMapping("/notice")
    public String noticeList(Model m) throws Exception {

        List<NoticeDto> noticeList = noticeDao.selectList(1);
        m.addAttribute("noticeList", noticeList);
        System.out.println("noticeList = " + noticeList);

        return "noticeList";
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
