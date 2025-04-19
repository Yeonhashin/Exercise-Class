package com.example.BaseProject.controller;

import com.example.BaseProject.dao.ClassTypeDao;
import com.example.BaseProject.dao.UserReservationDao;
import com.example.BaseProject.domain.ClassInfoDto;
import com.example.BaseProject.domain.ClassTypeDto;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/class")
public class UserClassController {
    @Autowired
    UserClassService userClassService;

    private final UserReservationService userReservationService;

    public UserClassController(UserReservationService userReservationService) {
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

    @Autowired
    private ClassTypeDao classTypeDao;

    @GetMapping("/list")
    public String classList(@RequestParam(value = "startDate", required = false) String startDateStr,
                            @RequestParam(value = "searchDate", required = false) String searchDate,
                            @RequestParam(value = "searchClassName", required = false) String searchClassName,
                            Model m, HttpSession session, RedirectAttributes rattr) {
        try {
            // 메시지를 flashAttributes로 전달
            if (rattr.getFlashAttributes().get("msg") != null) {
                m.addAttribute("msg", rattr.getFlashAttributes().get("msg"));
            }

            // 1. 현재 로그인한 유저 정보 가져오기
            int userId = (int) session.getAttribute("user_id");

            List<Integer> reservedClassIds = userReservationDao.findReservedClassIdsByUser(userId);

            LocalDate startDate;
            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = LocalDate.parse(startDateStr); // yyyy-MM-dd 형식이어야 함
            } else {
                startDate = LocalDate.now(); // 기본값: 오늘 날짜
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d(E)");

            List<String> formattedDates = IntStream.range(0, 5)
                    .mapToObj(i -> startDate.plusDays(i).format(formatter)) // 포맷 적용
                    .collect(Collectors.toList());

            // 날짜 목록을 생성 (예시: 3일 범위로 날짜 표시)
            List<LocalDate> dateList = getDatesInRange(startDate);
            Collections.sort(dateList);

            // 시간별 -> 날짜별 수업 정보 저장을 위한 Map
            Map<String, Map<String, List<ClassInfoDto>>> scheduleMap = new TreeMap<>();

            List<String> times = List.of("10:00", "11:30", "13:00", "15:30", "17:00", "19:30", "21:00");

            for (String time : times) {
                for (LocalDate date : dateList) {
                    String formattedDate = date.format(formatter); // 날짜 포맷

                    // 시간 키가 없으면 초기화
                    scheduleMap.computeIfAbsent(time, k -> new TreeMap<>());

                    // 날짜별 해당 시간의 수업 정보 저장
                    scheduleMap.get(time).put(formattedDate, userClassService.getClassByDateAndTime(date, time));
                }
            }

                List<ClassInfoDto> filteredClasses = userClassService.search(searchDate, searchClassName);
//                List<ClassTypeDto> allClassNames = classTypeDto.getClass_name();
                List<ClassTypeDto> list = classTypeDao.selectAll();

                List<String> allClassNames = list.stream()
                        .map(ClassTypeDto::getClass_name)
                        .collect(Collectors.toList());

                m.addAttribute("classList", filteredClasses); // 결과 리스트
                m.addAttribute("selectedDate", searchDate);
                m.addAttribute("selectedClassName", searchClassName);
                m.addAttribute("classNames", allClassNames);

            System.out.println("classList = " + filteredClasses);
                
            m.addAttribute("scheduleMap", scheduleMap); // 변환된 시간-날짜 맵을 전달
            m.addAttribute("formattedDates", formattedDates);
            m.addAttribute("startDate", startDate);
            m.addAttribute("times", times);
            m.addAttribute("reservedClassIds", reservedClassIds);  // 추가!
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "classList"; // 화면으로 이동
    }

    private List<LocalDate> getDatesInRange(LocalDate startDate) {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {  // 예시로 7일 범위로 날짜 표시
            dates.add(startDate.plusDays(i));
        }
        return dates;
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("email")!=null;
    }
}
