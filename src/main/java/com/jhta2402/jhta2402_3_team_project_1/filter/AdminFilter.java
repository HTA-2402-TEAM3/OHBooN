package com.jhta2402.jhta2402_3_team_project_1.filter;

import com.jhta2402.jhta2402_3_team_project_1.dto.Grade;
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


@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // admin 계정으로 로그인하지 않은 사람이 admin에 접속할 경우 무조건 index으로 이동

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpSession session = req.getSession(); // 세션 생성

        Grade grade = null;
        if(session.getAttribute("sessionGrade")!=null){
            grade = (Grade) session.getAttribute("sessionGrade");
        }

        if(grade == null){
            resp.sendRedirect(req.getContextPath() + "/index/index");
        }else if(grade.equals(Grade.ADMIN) || grade.equals(Grade.MANAGER)) {
            System.out.println("grade admin true");
            chain.doFilter(request, response);
        }else {
//            ModalDto modalDto = new ModalDto("알림", "잘못된 접근입니다.", "show");
//            req.getSession().setAttribute("modal", modalDto);
            resp.sendRedirect(req.getContextPath() + "/index/index");
        }
    }
}

