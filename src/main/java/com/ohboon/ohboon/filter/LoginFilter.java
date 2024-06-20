package com.ohboon.ohboon.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebFilter({"/user/login/*", "/user/signup/*"})
public class LoginFilter implements Filter {

    public LoginFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //로그인한 사람이 접근할 경우 index로 강제이동
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(); // 세션 생성

        if(session.getAttribute("sessionEmail")!=null){
            // 모달 메시지 생성 및 전달
//            ModalDto modalDto = new ModalDto("알림", "잘못된 접근", "show");
//            req.setAttribute("modal", modalDto);
            resp.sendRedirect(req.getContextPath() + "/index/index");

        }else {
            chain.doFilter(request, response);

        }
    }
}

