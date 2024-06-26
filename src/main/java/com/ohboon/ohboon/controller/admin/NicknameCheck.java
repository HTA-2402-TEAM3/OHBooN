package com.ohboon.ohboon.controller.admin;

import com.ohboon.ohboon.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

// 관리자 페이지에서 닉네임 중복체크
@WebServlet("/admin/nicknameCheck")
public class NicknameCheck extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("nickname");
        UserDAO userDao = new UserDAO();

        boolean exists = userDao.isNicknameExists(nickname);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("{\"exists\": " + exists + "}");
        out.flush();
    }
}
