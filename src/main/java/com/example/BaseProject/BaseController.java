package com.example.BaseProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {
    @RequestMapping("/hello")
    public String home(){
        return"hello";
    }
}
