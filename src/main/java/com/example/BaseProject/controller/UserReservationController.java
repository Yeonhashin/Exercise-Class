package com.example.BaseProject.controller;

import com.example.BaseProject.domain.UserReservationDto;
import com.example.BaseProject.service.UserClassService;
import com.example.BaseProject.service.UserReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reservation")
public class UserReservationController {
    @Autowired
    UserClassService userClassService;

    private final UserReservationService userReservationService;

    public UserReservationController(UserReservationService userReservationService) {
        this.userReservationService = userReservationService;
    }

    @GetMapping("/list")
    public String getReservedList(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(defaultValue = "5") int size,
            Model model, HttpSession session) {
        try {
            int userId = getUserIdFromSession(session);
            model.addAttribute("hasMore", userReservationService.hasMore(offset + size, userId));
            model.addAttribute("reservedClass", userReservationService.reservedAllClassByUser(userId, offset, size));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "reservedList";
    }

    @GetMapping("/list/more")
    @ResponseBody
    public Map<String, Object> loadMoreReserved(@RequestParam int offset,
                                                @RequestParam(defaultValue = "5") int size,
                                                HttpSession session) throws Exception {
        int userId = getUserIdFromSession(session);
        boolean hasMore = userReservationService.hasMore(offset + size, userId);
        List<UserReservationDto> reservedClass = userReservationService.reservedAllClassByUser(userId, offset, size);

        Map<String, Object> response = new HashMap<>();
        response.put("reservedClass", reservedClass);
        response.put("hasMore", hasMore);
        return response;
    }

    // reserveClass :: 수업 예약
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void reserveClass(@RequestParam("classId") int classId,
                             HttpSession session,
                             HttpServletResponse response) {
        try {
            int userId = getUserIdFromSession(session);
            int result = userReservationService.reserveClass(userId, classId);
            if (result > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"result\":\"success\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"result\":\"fail\", \"message\":\"예약 실패\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"result\":\"fail\", \"message\":\"서버 오류\"}");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public void cancelReservation(@RequestParam("reservationId") int reservationId,
                                  HttpSession session,
                                  HttpServletResponse response) {
        try {
            int userId = getUserIdFromSession(session);
            int result = userReservationService.cancelReservation(userId, reservationId);
            if (result > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"result\":\"success\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"result\":\"fail\", \"message\":\"취소 실패\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"result\":\"fail\", \"message\":\"서버 오류\"}");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private int getUserIdFromSession(HttpSession session) {
        Object userId = session.getAttribute("user_id");
        if (userId instanceof Integer) return (Integer) userId;
        throw new IllegalStateException("로그인이 필요합니다.");
    }


}
