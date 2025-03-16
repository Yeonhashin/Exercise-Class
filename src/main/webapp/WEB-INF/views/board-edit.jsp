<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pilates K :: Class - Edit</title>
    <!-- Bootstrap 5 CSS -->
    <link rel="stylesheet" href="<c:url value='/css/common.css'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="layout/header.jsp" %>
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
<script>
    let msg = "${msg}"
    if(msg == "WRT_ERR") alert("게시물 등록에 실패했습니다. 다시 시도해주세요.");
    if(msg == "MOD_ERR") alert("게시물 수정에 실패했습니다. 다시 시도해주세요.");
</script>
<div style="text-align:center">
    <h2>수업 ${mode=="new" ? "등록하기" : "수정하기"}</h2>
    <form action="" id="form">
        <input type="hidden" name="id" value="${boardDto.id}" readonly="readonly"}>
        <div>지점명</div>
        <select id="class_point_id" name="class_point_id">
            <option name="ck_option" disabled selected>선택해주세요</option>
            <c:forEach var="point" items="${points}">
                <option value="${point.id}" ${mode!="new" && boardDto.class_point_id eq point.id ? "selected" : ""}>${point.class_point_name}</option>
            </c:forEach>
        </select>
        <div>강사명</div>
        <select id="instructor_id" name="instructor_id">
            <option name="ck_option" disabled selected>선택해주세요</option>
            <c:forEach var="instructor" items="${instructors}">
                <option value="${instructor.id}" ${mode!="new" && boardDto.instructor_id eq instructor.id ? "selected" : ""}>${instructor.instructor_name}</option>
            </c:forEach>
        </select>
        <div>수업명</div>
        <select id="class_type_id" name="class_type_id">
            <option name="ck_option" disabled selected>선택해주세요</option>
            <c:forEach var="type" items="${types}">
                <option value="${type.id}" ${mode!="new" && boardDto.class_type_id eq type.id ? "selected" : ""}>${type.class_name}</option>
            </c:forEach>
        </select>
        <div>수업일</div>
        <input type="date" name="class_date" value="${boardDto.class_date}">
        <div>수업시간</div>
        <select id="class_time_id" name="class_time_id">
            <option name="ck_option" disabled selected>선택해주세요</option>
            <c:forEach var="time" items="${times}">
                <option value="${time.id}" ${mode!="new" && boardDto.class_time_id eq time.id ? "selected" : ""}>${time.class_start_time} - ${time.class_end_time}</option>
            </c:forEach>
        </select>
        <div>
            <button type="button" id="writeBtn" class="btn">${mode!="new" ? "수정" : "등록"}</button>
            <button type="button" id="removeBtn" class="btn" ${mode!="new" ? "":"hidden"}>삭제</button>
            <button type="button" id="listBtn" class="btn">목록</button>
        </div>
    </form>
</div>

<!-- 추가 콘텐츠 섹션 -->
<div class="container mt-5 pt-5">
    <h2>Section Title</h2>
    <p>Some content goes here. Add your text, images, or other elements.</p>
</div>

<script>
    $(document).ready(function() {
        if(${mode!="new"})
            $("option[name=ck_option]").css("display", "none");

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

        $('#writeBtn').on("click", function () {
            let form = $('#form');
            let actionUrl = ${mode == "new"} ? "<c:url value='/board/write'/>" : "<c:url value='/board/modify'/>";

            form.attr({
                "action": actionUrl,
                "method": "post"
            }).submit();
        });

    });
</script>
<!-- Bootstrap 5 JS 및 Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>