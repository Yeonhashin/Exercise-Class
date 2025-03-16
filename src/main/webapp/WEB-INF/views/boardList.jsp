<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html data-bs-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pilates K :: Class</title>
    <!-- Bootstrap 5 CSS -->
    <link rel="stylesheet" href="<c:url value='/css/common.css'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="layout/header.jsp" %>
<script>
    let msg = "${msg}"
    if(msg == "WRT_OK") alert("성공적으로 등록되었습니다.");
    if(msg == "MOD_OK") alert("성공적으로 수정되었습니다.");
    if(msg == "DEL_OK") alert("성공적으로 삭제되었습니다.");
    if(msg == "DEL_ERR") alert("삭제에 실패했습니다.");
</script>
<div style="text-align:center">
    <button type="button" id="writeBtn" onclick="location.href='<c:url value="/board/write"/>'">수업등록</button>
    <table border="1">
        <tr>
            <th>No</th>
            <th>class_name</th>
            <th>class_type</th>
            <th>class_point</th>
            <th>instructor</th>
            <th>date</th>
        </tr>
        <c:forEach var="boardDto" items="${list}">
            <tr>
                <td>${boardDto.id}</td>
                <td>
                    <a href="<c:url value='/board/read?id=${boardDto.id}&page=${page}&pageSize=${pageSize}'/>">
                        ${boardDto.class_name}
                    </a>
                </td>
                <td>${boardDto.class_type_name}</td>
                <td>${boardDto.class_point_name}</td>
                <td>${boardDto.instructor_name}</td>
                <td>${boardDto.class_date}</td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <div>
        <c:if test="${ph.showPrev}">
            <a href="<c:url value='/board/list?page=${ph.beginPage-1}&pageSize=${ph.pageSize}'/>">&lt;</a>
        </c:if>
        <c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
            <a href="<c:url value='/board/list?page=${i}&pageSize=${ph.pageSize}'/>">${i}</a>
        </c:forEach>
        <c:if test="${ph.showNext}">
            <a href="<c:url value='/board/list?page=${ph.endPage+1}&pageSize=${ph.pageSize}'/>">&gt;</a>
        </c:if>
    </div>
</div>
<!-- 추가 콘텐츠 섹션 -->
<div class="container mt-5 pt-5">
    <h2>Section Title</h2>
    <p>Some content goes here. Add your text, images, or other elements.</p>
</div>

<!-- 풋터 -->
<%@ include file="layout/footer.jsp" %>

<!-- Bootstrap 5 JS 및 Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>
