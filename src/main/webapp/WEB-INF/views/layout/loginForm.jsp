<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pilates K :: Home</title>
    <!-- Bootstrap 5 CSS -->
    <link rel="stylesheet" href="<c:url value='/css/common.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/login.css'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- 헤더 (상단 네비게이션 바) -->
<%@ include file="header.jsp" %>
<form action="<c:url value="/login/login"/>" method="post" onsubmit="return formCheck(this);">
        <h3 id="title">Login</h3>
        <div id="msg">
            <c:if test="${not empty param.msg}">
                <i class="fa fa-exclamation-circle"> ${URLDecoder.decode(param.msg)}</i>
            </c:if>
        </div>
        <input type="text" name="email" value="${cookie.email.value}" placeholder="이메일 입력" autofocus>
        <input type="password" name="password" placeholder="비밀번호">
        <input type="hidden" name="toURL" value="${param.toURL}">
        <button>로그인</button>
        <div>
            <label><input type="checkbox" name="rememberId" value="on" ${empty cookie.email.value ? "":"checked"}> 아이디 기억</label> |
            <a href="">비밀번호 찾기</a> |
            <a href="">회원가입</a>
        </div>
    </form>
<!-- 풋터 -->
<%@ include file="footer.jsp" %>

<!-- Bootstrap 5 JS 및 Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
<script>
    function formCheck(frm) {
        let msg ='';
        if(frm.email.value.length==0) {
            setMessage('이메일을 입력해주세요.', frm.email);
            return false;
        }
        if(frm.password.value.length==0) {
            setMessage('패스워드를 입력해주세요.', frm.password);
            return false;
        }
        return true;
    }
    function setMessage(msg, element){
        document.getElementById("msg").innerHTML = ` ${'${msg}'}`;
        if(element) {
            element.select();
        }
    }
</script>
</body>
</html>