package com.ohboon.ohboon.filter;

import com.ohboon.ohboon.dto.ModalDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebFilter( {"/board/*"} )
public class BoardFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //로그인하지 않은 사람이 board에 접속할 경우 무조건 login으로 강제이동
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(); // 세션 생성

        if(session.getAttribute("sessionID")!=null){
            chain.doFilter(request, response);
        }else {
            // 모달 메시지 생성 및 전달
            ModalDto modalDto = new ModalDto("알림", "게시판 입장을 위해 로그인을 해주세요.", "show");
            req.setAttribute("modal", modalDto);
            req.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(req, response);
            //ScriptWriter.alertAndNext(resp, "로그인 페이지로 이동합니다.", req.getContextPath() + "/user/login");
        }
    }
}
