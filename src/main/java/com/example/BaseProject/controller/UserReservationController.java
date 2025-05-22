package com.example.BaseProject.controller;

import com.example.BaseProject.dao.UserReservationDao;
import com.example.BaseProject.domain.UserReservationDto;
import com.example.BaseProject.service.UserClassService;
import com.example.BaseProject.service.UserReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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

    @Service
    public class ReservationService {
        public void cancelReservation(int userId, int classId) {
            System.out.println("예약 취소: User ID = " + userId + ", Class ID = " + classId);
        }
    }

    @Autowired
    private UserReservationDao userReservationDao;

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("email") != null;
    }

    @PostMapping("/add")
    public String reserveClass(
            @RequestParam("class_id") int class_id,
            UserReservationDto Dto, Model m, HttpSession session,
            RedirectAttributes rattr) {

        try {
            int userId = (int) session.getAttribute("user_id");
            Dto.setUser_id(userId);
            Dto.setClass_id(class_id);

            int result = userReservationService.reserveClass(Dto);
            if (result != 1) {
                throw new Exception("Modify failed");
            }

            // 예약 성공 메시지 추가
            rattr.addFlashAttribute("msg", "예약이 완료되었습니다!");
            rattr.addFlashAttribute("status", "success"); // 성공
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "예약에 실패했습니다. 다시 시도해주세요.");
            rattr.addFlashAttribute("status", "error"); // 실패
        }

        return "redirect:/class/list"; // 리다이렉트 후 flash 메시지 표시
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public void cancelReservation(@RequestParam("classId") int classId, HttpSession session, HttpServletResponse response) {
        try {
            Integer userId = (Integer) session.getAttribute("user_id");

            int result = userReservationService.cancelReservation(userId, classId);

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

    @GetMapping("/list")
    public String reservedList(HttpSession session, Model m, @RequestParam(value = "offset", required = false, defaultValue = "0") int offset) {
        try {
            int userId = (int) session.getAttribute("user_id");
            int size = 5;
            List<UserReservationDto> reservedClass = userReservationService.reservedAllClassByUser(userId, offset, size);
            boolean hasMore = userReservationService.hasMore(offset + size, userId);

            m.addAttribute("hasMore", hasMore);
            m.addAttribute("reservedClass", reservedClass);
            System.out.println("hasMore = " + hasMore);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "reservedList";
    }

    @GetMapping("/list/more")
    @ResponseBody
    public Map<String, Object> loadMoreReserved(@RequestParam int offset, @RequestParam(defaultValue = "5") int size, HttpSession session) throws Exception {

        int userId = (int) session.getAttribute("user_id");

        List<UserReservationDto> reservedClass = userReservationService.reservedAllClassByUser(userId, offset, size);

        boolean hasMore = userReservationService.hasMore(offset + size, userId);


        Map<String, Object> response = new HashMap<>();

        response.put("reservedClass", reservedClass);
        response.put("hasMore", hasMore);
        return response;
    }
}
