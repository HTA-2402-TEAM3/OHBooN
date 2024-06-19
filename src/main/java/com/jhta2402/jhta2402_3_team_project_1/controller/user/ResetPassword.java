package com.jhta2402.jhta2402_3_team_project_1.controller.user;

import com.jhta2402.jhta2402_3_team_project_1.dao.UserDao;
import com.jhta2402.jhta2402_3_team_project_1.utils.ScriptWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResetPassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        req.setAttribute("token", token);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user/reset-password.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String newPassword = req.getParameter("newPassword");

        UserDao userDao = new UserDao();
        if (userDao.isTokenValid(token)) {
            userDao.updatePasswordByToken(token, newPassword);
            ScriptWriter.alertAndNext(resp, "비밀번호가 성공적으로 변경되었습니다.", "/index/index");
        } else {
            ScriptWriter.alertAndBack(resp, "유효하지 않은 토큰입니다.");
        }
    }
}
