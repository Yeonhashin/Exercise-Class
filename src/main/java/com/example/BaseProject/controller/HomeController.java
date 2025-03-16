package com.example.BaseProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        // "index"는 templates/index.html을 렌더링합니다.
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
