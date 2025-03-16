<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pilates K :: Schedule</title>
    <!-- Bootstrap 5 CSS -->
    <link rel="stylesheet" href="<c:url value='/css/common.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/schedule.css'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- 헤더 (상단 네비게이션 바) -->
<%@ include file="header.jsp" %>

<div class="container mt-2">
    <div class="title">레슨 찾기</div>
    <p>예약할 날짜 / 시간대의 레슨을 선택하여 예약해주세요.</p>
    <p>지점 : <strong>Pilates K 고베점</strong></p>
    <p>날짜 : <strong>2025/2/23(금)</strong></p>

    <div class="button-container">
        <button>해당 조건으로 검색하기</button>
    </div>


<%--    <div class="table-navigation">--%>
<%--        <button>&lt; 이전 5일 보기</button>--%>
<%--        <button>이후 5일 보기 &gt;</button>--%>
<%--    </div>--%>

<%--    <table class="schedule-table">--%>
<%--        <thead>--%>
<%--        <tr>--%>
<%--            <th>시간</th>--%>
<%--            <th>2/23(금)</th>--%>
<%--            <th>2/24(토)</th>--%>
<%--            <th>2/25(일)</th>--%>
<%--            <th>2/26(월)</th>--%>
<%--            <th>2/27(화)</th>--%>
<%--        </tr>--%>
<%--        </thead>--%>
<%--        <tbody>--%>
<%--        <tr>--%>
<%--            <td>10:00 - 11:00</td>--%>
<%--            <td><span class="lesson">Basic</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Basic</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Basic</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Basic</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Basic</span>--%>
<%--                <button class="reserved">예약중</button><br>--%>
<%--                <button class="cancel-btn">예약취소하기</button>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>11:30 - 12:30</td>--%>
<%--            <td><span class="lesson">Shape Up Waist</span>--%>
<%--                <button class="waitlist-btn">대기예약하기</button>--%>
<%--                <div>대기1명</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Shape Up Waist</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Shape Up Waist</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Shape Up Waist</span>--%>
<%--                <button class="waitlist-btn">대기예약하기</button>--%>
<%--                <div>대기1명</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Shape Up Waist</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>15:30 - 16:30</td>--%>
<%--            <td><span class="lesson">Reset Flow</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Reset Flow</span>--%>
<%--                <button class="reserved">예약중</button><br>--%>
<%--                <button class="cancel-btn">예약취소하기</button>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Reset Flow</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Reset Flow</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Reset Flow</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>19:30 - 20:30</td>--%>
<%--            <td><span class="lesson">Style Up Pilates</span>--%>
<%--                <button class="reserved">예약중</button><br>--%>
<%--                <button class="cancel-btn">예약취소하기</button>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Style Up Pilates</span>--%>
<%--                <button class="waitlist-btn">대기예약하기</button>--%>
<%--                <div>대기1명</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Style Up Pilates</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Style Up Pilates</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--            <td><span class="lesson">Style Up Pilates</span>--%>
<%--                <button class="reserve-btn">예약하기</button>--%>
<%--                <div>-</div>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--        </tbody>--%>
<%--    </table>--%>

    <div class="table-navigation">
        <button th:onclick="|location.href='@{/schedule(startDate=${startDate.minusDays(5)})}'|">&lt; 이전 5일 보기</button>
        <button th:onclick="|location.href='@{/schedule(startDate=${startDate.plusDays(5)})}'|">이후 5일 보기 &gt;</button>
    </div>

    <table class="schedule-table">
        <thead>
        <tr>
            <th>시간</th>
            <th th:each="date : ${dateList}" th:text="${date}"></th>
        </tr>
        </thead>
        <tbody>
        <tr th:each="schedule : ${scheduleList}">
            <td th:text="${schedule.class_start_time} + ' - ' + ${schedule.class_end_time}"></td>
            <td>
                <span class="lesson" th:text="${schedule.class_name}"></span>
                <button th:if="${schedule.reservation_cnt == 0}" class="reserve-btn">예약하기</button>
<%--                <button th:if="${schedule.status == '예약중'}" class="reserved">예약중</button>--%>
<%--                <button th:if="${schedule.reservation_cnt < 10}" class="waitlist-btn">대기예약하기</button>--%>
<%--                <div th:if="${schedule.waitlistCount > 0}" th:text="'대기 ' + ${schedule.waitlistCount} + '명'"></div>--%>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<!-- 풋터 -->
<%@ include file="footer.jsp" %>

<!-- Bootstrap 5 JS 및 Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>
