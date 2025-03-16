<!-- 헤더 (상단 네비게이션 바) -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<c:set var="loginId" value="${pageContext.request.getSession(false)==null ? '' : pageContext.request.session.getAttribute('email')}"/>
<c:set var="loginLink" value="${loginId=='' ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId=='' ? 'Login' : 'Logout'}"/>

<header class="navbar navbar-expand-lg fixed-top">
    <div class="container">
        <a class="navbar-brand" href="#">Pilates K</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link active" href="/">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="/board/classList">Class</a></li>
                <li class="nav-item"><a class="nav-link" href="${loginLink}">${loginOut}</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Notice</a></li>
            </ul>
        </div>
    </div>
</header>