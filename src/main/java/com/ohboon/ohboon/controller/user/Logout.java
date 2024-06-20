package com.ohboon.ohboon.controller.user;

import com.ohboon.ohboon.utils.ScriptWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//로그아웃은 세션을 종료시키는 것

@WebServlet("/user/logout")
public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        ScriptWriter.alertAndNext(resp, "로그아웃되었습니다.", "../index/index");
    }
}
