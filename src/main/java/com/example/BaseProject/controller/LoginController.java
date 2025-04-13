package com.example.BaseProject.controller;

import com.example.BaseProject.domain.UserDto;
import com.example.BaseProject.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public String loginForm(Model m, HttpServletRequest request) {
        m.addAttribute("rememberEmail", request.getCookies());
        return "loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(String email, String password, String toURL, boolean rememberId, Model m,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map map = new HashMap();
        map.put("email", email);
        map.put("password", password);
        UserDto user = loginService.selectUser(map);
        if(user == null){
            String msg = URLEncoder.encode("이메일 혹은 패스워드가 일치하지 않습니다.", "utf-8");
            return "redirect:/login/login?msg="+msg;
        }
        HttpSession session = request.getSession();
        session.setAttribute("email", user.getEmail());
        session.setAttribute("name", user.getName());
        session.setAttribute("user_id", user.getId());


        if(rememberId) {
            Cookie cookie = new Cookie("email", String.valueOf(user.getEmail()));
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("email", String.valueOf(user.getEmail()));
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        // 쿠키에서 이메일을 추출하여 Model에 추가
        Cookie[] cookies = request.getCookies();
        String rememberEmail = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    rememberEmail = cookie.getValue();
                }
            }
        }
        m.addAttribute("rememberEmail", rememberEmail);  // Model에 저장

        if (toURL == null || toURL.isEmpty() || !toURL.startsWith("/")) {
            toURL = "/";
        }

        return "redirect:"+toURL;
    }

    @GetMapping("/register")
    public String registerForm(Model m, HttpServletRequest request) {
        return "registerForm";
    }

    @PostMapping("/register")
    public String register(String email, String password, String name, Model m, HttpServletRequest request) throws Exception {
        Map map = new HashMap();
        map.put("email", email);
        map.put("password", password);
        map.put("name", name);
        int insertUser = loginService.insertUser(map);
        if(insertUser == 0){
            String msg = URLEncoder.encode("회원가입에 실패하였습니다.", "utf-8");
            return "redirect:/login/register?msg="+msg;
        }

        return "redirect:/";
    }
}


