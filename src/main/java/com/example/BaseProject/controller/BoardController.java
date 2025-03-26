package com.example.BaseProject.controller;

import com.example.BaseProject.dao.UserReservationDao;
import com.example.BaseProject.domain.BoardDto;
import com.example.BaseProject.domain.InstructorDto;
import com.example.BaseProject.domain.PageHandler;
import com.example.BaseProject.domain.UserReservationDto;
import com.example.BaseProject.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    private UserReservationDao userReservationDao;

    @GetMapping("/write")
    public String write(Model m) {
        try {
            m.addAttribute("instructors",boardService.getInstructor());
            m.addAttribute("points",boardService.getClassPoint());
            m.addAttribute("types",boardService.getClassType());
            m.addAttribute("times",boardService.getClassTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.addAttribute("mode", "new");
        return "board-edit";
    }

    @PostMapping("/write")
    public String write(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr){
//        (int) class_point_id = (int)session.getAttribute("class_point_id");
        int class_point_id = 1;
        boardDto.setClass_point_id(class_point_id);
        try {
            int rowCnt = boardService.write(boardDto);

            if(rowCnt != 1)
                throw new Exception("Write failed");

            rattr.addFlashAttribute("msg", "WRT_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            rattr.addFlashAttribute("msg", "WRT_ERR");
            return "board-edit";
        }
    }

    @GetMapping("/modify")
    public String modify(Integer id, Model m) {
        try {
            BoardDto boardDto = boardService.read(id);
            //m.addAttribute("boardDto", boardDto);
            m.addAttribute(boardDto);
            m.addAttribute("instructors",boardService.getInstructor());
            m.addAttribute("points",boardService.getClassPoint());
            m.addAttribute("types",boardService.getClassType());
            m.addAttribute("times",boardService.getClassTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "board-edit";
    }

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr){
//        (int) class_point_id = (int)session.getAttribute("class_point_id");
        int class_point_id = 1;
        boardDto.setClass_point_id(class_point_id);
        try {
            int rowCnt = boardService.modify(boardDto);

            if(rowCnt != 1)
                throw new Exception("Modify failed");

            rattr.addFlashAttribute("msg", "MOD_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            rattr.addFlashAttribute("msg", "MOD_ERR");
            return "board-edit";
        }
    }

//    @PostMapping("/remove")
//    public String remove(Integer id, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr) {
////        int class_point_id = (int) session.getAttribute("class_point_id");
//        int class_point_id = 1;
//        try {
//            m.addAttribute("page", page);
//            m.addAttribute("pageSize", pageSize);
//
//            int rowCnt = boardService.remove(id, class_point_id);
//
//            if (rowCnt != 1)
//                throw new Exception("board remove error");
//
//            rattr.addFlashAttribute("msg", "DEL_OK");
//        } catch (Exception e) {
//            e.printStackTrace();
//            rattr.addFlashAttribute("msg", "DEL_ERR");
//        }
//
//        return "redirect:/board/list";
//    }

    @GetMapping("/read")
    public String read(Integer id, Integer page, Integer pageSize, Model m) {
        try {
            BoardDto boardDto = boardService.read(id);
            //m.addAttribute("boardDto", boardDto);
            m.addAttribute(boardDto);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "board";
    }

    @GetMapping("/list")
    public String list(Integer page, Integer pageSize, Model m, HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

        if(page == null) page = 1;
        if(pageSize == null) pageSize = 5;

        try {
            int totalCnt = boardService.getCount();
            PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);

            Map map = new HashMap();
            map.put("offset", (page-1)*pageSize);
            map.put("pageSize", pageSize);

            List<BoardDto> list = boardService.getPage(map);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    @GetMapping("/classList")
    public String classList(@RequestParam(value = "startDate", required = false)
                                String startDateStr, Model m, HttpSession session,
                                RedirectAttributes rattr) {
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
            Map<String, Map<String, List<BoardDto>>> scheduleMap = new TreeMap<>();

            List<String> times = List.of("10:00", "11:30", "13:00", "15:30", "17:00", "19:30", "21:00");

            for (String time : times) {
                for (LocalDate date : dateList) {
                    String formattedDate = date.format(formatter); // 날짜 포맷

                    // 시간 키가 없으면 초기화
                    scheduleMap.computeIfAbsent(time, k -> new TreeMap<>());

                    // 날짜별 해당 시간의 수업 정보 저장
                    scheduleMap.get(time).put(formattedDate, boardService.getClassByDateAndTime(date, time));
                }
            }
            m.addAttribute("scheduleMap", scheduleMap); // 변환된 시간-날짜 맵을 전달
            m.addAttribute("formattedDates", formattedDates);
            m.addAttribute("startDate", startDate);
            m.addAttribute("times", times);
            m.addAttribute("reservedClassIds", reservedClassIds);  // 추가!

            System.out.println("예약된 클래스 ID들: " + reservedClassIds);

            System.out.println("scheduleMap = " + scheduleMap);
            System.out.println("dateList = " + dateList);
            System.out.println("times = " + times);
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

    @PostMapping("/reserve")
    public String reserveClass(
            @RequestParam("class_id") int class_id,
            UserReservationDto Dto, Model m, HttpSession session,
            RedirectAttributes rattr) {

        try {
            int userId = (int) session.getAttribute("user_id");
            Dto.setUser_id(userId);
            Dto.setClass_id(class_id);

            int result = boardService.reserveClass(Dto);
            if(result != 1) {
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

        return "redirect:/board/classList"; // 리다이렉트 후 flash 메시지 표시
    }

    @RequestMapping(value = "/reservation/cancel", method = RequestMethod.POST)
    public void cancelReservation(@RequestParam("classId") int classId, HttpSession session, HttpServletResponse response) {
        try {
            Integer userId = (Integer) session.getAttribute("user_id");

            int result = boardService.cancelReservation(userId, classId);

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

    @GetMapping("/reservedList")
    public String reservedList(HttpSession session, Model m) {
        try {
            int userId = (int) session.getAttribute("user_id");

            List<UserReservationDto> reservedClass = boardService.reservedAllClassByUser(userId);
            m.addAttribute("reservedClass", reservedClass);
            System.out.println("reservedClass11111 = " + reservedClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "reservedList";
    }





}
