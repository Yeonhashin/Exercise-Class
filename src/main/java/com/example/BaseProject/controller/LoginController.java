package com.example.BaseProject.controller;

import com.example.BaseProject.domain.UserDto;
import com.example.BaseProject.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        Cookie[] cookies = request.getCookies();
        String rememberEmail = "";

        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("rememberEmail".equals(c.getName())) {
                    rememberEmail = c.getValue();
                }
            }
        }

        m.addAttribute("rememberEmail", rememberEmail);

        return "loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(String email, String password, String toURL, @RequestParam(defaultValue = "false") boolean rememberId, Model m,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map map = new HashMap();
        map.put("email", email);
        map.put("password", password);
        UserDto user = loginService.selectUser(map);
        if (user == null) {
            m.addAttribute("msg", "이메일 또는 비밀번호가 올바르지 않습니다.");
            m.addAttribute("email", email);
            m.addAttribute("error", true);
            return "loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute("email", user.getEmail());
        session.setAttribute("name", user.getName());
        session.setAttribute("user_id", user.getId());

        if (rememberId) {
            Cookie cookie = new Cookie("rememberEmail", email);
            cookie.setMaxAge(60 * 60 * 24 * 7); // 7일 유지
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("rememberEmail", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        if (toURL == null || toURL.isEmpty() || !toURL.startsWith("/")) {
            toURL = "/";
        }

        return "redirect:" + toURL;
    }

    @GetMapping("/register")
    public String registerForm(Model m, HttpServletRequest request) {
        return "registerForm";
    }

    @PostMapping("/register")
    public String register(String email, String password, String name, Model m, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = new HashMap();
        map.put("email", email);
        map.put("password", password);
        map.put("name", name);
        int insertUser = loginService.insertUser(map);
        if (insertUser == 0) {
            String msg = URLEncoder.encode("회원가입에 실패하였습니다.", "utf-8");
            return "redirect:/login/register?msg=" + msg;
        }

        Cookie cookie = new Cookie("rememberEmail", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }
}


