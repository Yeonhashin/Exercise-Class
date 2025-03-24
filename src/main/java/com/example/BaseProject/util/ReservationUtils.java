package com.example.BaseProject.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationUtils {
        public static boolean canCancel(String classDateStr, String classStartTimeStr) {
            // 날짜와 시간을 LocalDate, LocalTime으로 파싱
            LocalDate classDate = LocalDate.parse(classDateStr); // 예: "2025-03-21"

            // 시간 파싱 (HH:mm 포맷)
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime classStartTime = LocalTime.parse(classStartTimeStr, timeFormatter);

            // 수업 시작 시간 = 날짜 + 시간
            LocalDateTime classStartDateTime = LocalDateTime.of(classDate, classStartTime);

            // 현재 시간 가져오기
            LocalDateTime now = LocalDateTime.now();

            // 현재 시간에서 1시간 더한 시간이 수업 시작 시간보다 이후면 취소 불가
            if (now.plusHours(1).isAfter(classStartDateTime)) {
                return false; // 1시간 미만으로 남음 -> 취소 불가
            }

            return true; // 취소 가능
        }

        public void main(String[] args) {
            String classDate = "2025-03-21";
            String classStartTime = "10:00";

            boolean canCancel = canCancel(classDate, classStartTime);

            if (canCancel) {
                System.out.println("예약 취소 가능!");
            } else {
                System.out.println("수업 시작 1시간 이내입니다. 예약 취소 불가!");
            }
        }
    }
