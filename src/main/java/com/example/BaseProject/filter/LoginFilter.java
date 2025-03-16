package com.example.BaseProject.filter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginFilter implements Filter {

    // 필터 초기화
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필요한 초기화 작업을 여기서 할 수 있습니다.
    }

    // 필터 처리
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 세션에서 사용자 정보 확인
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            // 로그인되지 않았다면 로그인 페이지로 리다이렉트
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login/login");
            return; // 더 이상 필터 체인을 계속 진행하지 않음
        }

        // 로그인 되어 있으면 요청을 계속 처리
        chain.doFilter(request, response);
    }

    // 필터 종료
    @Override
    public void destroy() {
        // 필터 종료 시 처리할 작업이 있으면 여기에 작성
    }
}
