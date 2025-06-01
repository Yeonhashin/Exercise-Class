package com.example.BaseProject.controller;

import com.example.BaseProject.dao.ClassTypeDao;
import com.example.BaseProject.dao.InstructorDao;
import com.example.BaseProject.domain.ClassInfoDto;
import com.example.BaseProject.domain.ClassTypeDto;
import com.example.BaseProject.domain.InstructorDto;
import com.example.BaseProject.service.UserClassService;
import com.example.BaseProject.service.UserReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/class")
public class UserClassController {
    @Autowired
    UserClassService userClassService;

    @Autowired
    private InstructorDao instructorDao;

    @Autowired
    private ClassTypeDao classTypeDao;

    private final UserReservationService userReservationService;

    public UserClassController(UserReservationService userReservationService) {
        this.userReservationService = userReservationService;
    }

    // getClassList :: 수업 일람 표시
    @GetMapping("/list")
    public String getClassList(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "searchClassDate", required = false) String searchClassDate,
            @RequestParam(value = "searchClassName", required = false) String searchClassName,
            @RequestParam(value = "searchInstructor", required = false) String searchInstructor,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "5") int days,
            Model model, HttpSession session) {

        try {
            int userId = getUserIdFromSession(session);
            LocalDate startDate = (startDateStr != null && !startDateStr.isEmpty())
                    ? LocalDate.parse(startDateStr) : LocalDate.now();

            addCalendarAttributes(model, startDate, days, userId);
            addSearchAttributes(model, searchClassDate, searchClassName, searchInstructor, offset, size, userId);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "수업 데이터를 불러오는 중 오류가 발생했습니다.");
        }
        return "classList";
    }

    // getClassListLoadMore :: 더보기 버튼 클릭시 수업 일람 표시
    @GetMapping("/list/more")
    @ResponseBody
    public Map<String, Object> getClassListLoadMore(
            @RequestParam(value = "searchClassDate", required = false) String searchClassDate,
            @RequestParam(value = "searchClassName", required = false) String searchClassName,
            @RequestParam(value = "searchInstructor", required = false) String searchInstructor,
            @RequestParam int offset,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session) throws Exception {

        int userId = getUserIdFromSession(session);
        List<ClassInfoDto> searchedClasses = userClassService.search(searchClassDate, searchClassName, searchInstructor, offset, size, userId);
        Map<String, List<ClassInfoDto>> grouped = searchedClasses.stream()
                .collect(Collectors.groupingBy(ClassInfoDto::getClass_date, LinkedHashMap::new, Collectors.toList()));

        Map<String, Object> response = new HashMap<>();
        response.put("searchExecuted", isSearchPerformed(searchClassDate, searchClassName, searchInstructor));
        response.put("moreClasses", grouped);
        response.put("hasMore", userClassService.hasMore(offset + size, searchClassDate, searchClassName, searchInstructor));
        return response;
    }

    private void addCalendarAttributes(Model model, LocalDate startDate, int days, int userId) throws Exception {
        model.addAttribute("scheduleMap", userClassService.getScheduleMap(startDate, days, userId));
        model.addAttribute("formattedDates", userClassService.getFormattedDates(startDate, days));
        model.addAttribute("times", userClassService.getClassTimes());
    }

    private void addSearchAttributes(Model model, String date, String name, String instructor, int offset, int size, int userId) throws Exception {
        List<ClassInfoDto> result = userClassService.search(date, name, instructor, offset, size, userId);
        Map<String, List<ClassInfoDto>> grouped = result.stream()
                .collect(Collectors.groupingBy(ClassInfoDto::getClass_date, LinkedHashMap::new, Collectors.toList()));

        model.addAttribute("groupedClassMap", grouped);
        model.addAttribute("hasMore", userClassService.hasMore(offset + size, date, name, instructor));
        model.addAttribute("searchExecuted", isSearchPerformed(date, name, instructor));
        model.addAttribute("selectedDate", date);
        model.addAttribute("selectedClassName", name);
        model.addAttribute("selectedInstructor", instructor);
        model.addAttribute("classNames", classTypeDao.selectAll().stream().map(ClassTypeDto::getClass_name).collect(Collectors.toList()));
        model.addAttribute("instructors", instructorDao.selectAll().stream().map(InstructorDto::getInstructor_name).collect(Collectors.toList()));
    }

    private boolean isSearchPerformed(String date, String name, String instructor) {
        return Stream.of(date, name, instructor).anyMatch(s -> s != null && !s.isEmpty());
    }

    private int getUserIdFromSession(HttpSession session) {
        Object userId = session.getAttribute("user_id");
        if (userId instanceof Integer) return (Integer) userId;
        throw new IllegalStateException("로그인이 필요합니다.");
    }
}
