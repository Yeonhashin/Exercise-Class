<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li><a href="<c:url value='/login/login'/>">login</a></li>
<%--        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>--%>
        <li><a href=""><i class="fas fa-search small"></i></a></li>
    </ul>
</div>
<div style="text-align:center">
    <h2>수업 설명</h2>
    <form action="" id="form">
        <input type="hidden" name="id" value="${boardDto.id}" readonly="readonly"}>
        <div>지점명</div>
        <div>${boardDto.class_point_name}</div>
        <div>강사명</div>
        <div>${boardDto.instructor_name}</div>
        <div>수업명</div>
        <div>${boardDto.class_name}</div>
        <div>수업일</div>
        <div>${boardDto.class_date}</div>
        <div>수업시간</div>
        <div>${boardDto.class_start_time} - ${boardDto.class_end_time}</div>
        <div>수업메모</div>
        <div>${boardDto.class_memo}</div>
        <button type="button" id="modifyBtn" class="btn">수정</button>
<%--        <button type="button" id="modifyBtn" class="btn">수정</button>--%>
        <button type="button" id="removeBtn" class="btn">삭제</button>
        <button type="button" id="listBtn" class="btn">목록</button>
    </form>
</div>
<script>
    $(document).ready(function() {
        $('#listBtn').on("click", function(){
            location.href = "<c:url value='/board/list'/>?page=${page}&pageSize=${pageSize}";
        })

        $('#removeBtn').on("click", function(){
            if(!confirm("정말로 삭제하시겠습니까?")) return;
            let form = $('#form');
            form.attr("action", "<c:url value='/board/remove'/>?page=${page}&pageSize=${pageSize}");
            form.attr("method", "post");
            form.submit();
        })

        $('#modifyBtn').on("click", function(){
            location.href = "<c:url value='/board/modify'/>?id=${boardDto.id}";
        })

    });
</script>
</body>
</html>