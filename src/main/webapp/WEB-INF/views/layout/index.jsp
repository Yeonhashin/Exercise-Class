<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pilates K :: Home</title>
    <!-- Bootstrap 5 CSS -->
    <link rel="stylesheet" href="<c:url value='/css/common.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/home.css'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- 헤더 (상단 네비게이션 바) -->
<%@ include file="header.jsp" %>
<!-- 슬라이드 쇼 (배경 이미지 및 텍스트) -->
<div id="carouselExampleCaptions" class="carousel slide carousel-fade" data-bs-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="/img/main_pc.png" class="d-block w-100" alt="log">
        </div>
        <div class="carousel-item">
            <img src="/img/main_pc.png" class="d-block w-100" alt="log">
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>

<!-- 추가 콘텐츠 섹션 -->
<div class="container mt-2">
    <h2>예약한 레슨 (최근 3건)</h2>
    <p>
        예약한 수업을 표시합니다.<br>
        예약을 캔슬 하고 싶으시면 [삭제] 버튼을 눌러주세요<br>
        ※삭제 기한이 지난 수업은 삭제가 되지 않습니다.
    </p>
    <table>
        <thead>
        <tr>
            <th>수업시간</th>
            <th>레슨명</th>
            <th>예약코스</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                2025/2/19 (월)<br>
                18:00 - 19:00<br>
                yuri.Y<br>
                Pilates K 고베
            </td>
            <td class="lesson-title">Shape up waist</td>
            <td>monthly</td>
            <td>
                <button class="delete-btn">× 삭제</button>
            </td>
        </tr>
        <tr>
            <td>
                2025/2/19 (월)<br>
                18:00 - 19:00<br>
                yuri.Y<br>
                Pilates K 고베
            </td>
            <td class="lesson-title">Shape up waist</td>
            <td>monthly</td>
            <td>
                <button class="delete-btn">× 삭제</button>
            </td>
        </tr>
        <tr>
            <td>
                2025/2/19 (월)<br>
                18:00 - 19:00<br>
                yuri.Y<br>
                Pilates K 고베
            </td>
            <td class="lesson-title">Shape up waist</td>
            <td>monthly</td>
            <td>
                <button class="delete-btn">× 삭제</button>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="button-container">
        <button>레슨을 예약하기</button>
        <button>모든 예약을 확인하기</button>
        <button>레슨 이력을 확인하기</button>
    </div>
</div>

<!-- 풋터 -->
<%@ include file="footer.jsp" %>

<!-- Bootstrap 5 JS 및 Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>
