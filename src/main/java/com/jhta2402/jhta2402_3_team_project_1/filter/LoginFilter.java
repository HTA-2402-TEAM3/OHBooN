package com.jhta2402.jhta2402_3_team_project_1.filter;


import com.jhta2402.jhta2402_3_team_project_1.dto.ModalDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

// (참고) Filter 안에 있는 init, destroy 메서드가 디폴트 값으로 존재하기 때문에 이 두 개는 따로 작성하지 않아도 무방함

// filter intercepter(Spring context)

// 프론트에서 데이터가 넘어 오고 나갈 때 필터를 거쳐서 감

// Spring을 사용할 경우 Sptring context 안에서 동작하는 interceptor가 필터와 비슷한 역할을 수행함.


@WebFilter( {"/board/*"} )
public class LoginFilter implements Filter {
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
            req.getRequestDispatcher("/WEB-INF/member/login.jsp").forward(req, response);

//            HttpServletResponse resp = (HttpServletResponse) response;
//            resp.sendRedirect("../member/login");

            //ScriptWriter.alertAndNext(resp, "로그인 페이지로 이동합니다.", req.getContextPath() + "/member/login");
        }
    }
}

@WebFilter( {"/member/login/*"} )
class LoginFilter2 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //로그인한 사람이 접근할 경우 index로 강제이동
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(); // 세션 생성

        if(session.getAttribute("sessionID")!=null){
            // 모달 메시지 생성 및 전달
            ModalDto modalDto = new ModalDto("알림", "잘못된 접근", "show");
            req.setAttribute("modal", modalDto);
            resp.sendRedirect(req.getContextPath() + "/index/index");

        }else {
            chain.doFilter(request, response);

        }
    }
}

