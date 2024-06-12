package com.jhta2402.jhta2402_3_team_project_1.controller.index;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/index/index")
public class Index extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ModalDto modalDto = new ModalDto("타이틀", "메시지 내용","상태");
//        req.setAttribute("modal", modalDto);
//
//        // 특정 세션 값 삭제
//        HttpSession session = req.getSession();`
//        session.removeAttribute("title");
//        session.removeAttribute("msg");
//        session.removeAttribute("isState");

        RequestDispatcher dispatcher= req.getRequestDispatcher("/WEB-INF/index/index.jsp");
        dispatcher.forward(req,resp);

    }
}
