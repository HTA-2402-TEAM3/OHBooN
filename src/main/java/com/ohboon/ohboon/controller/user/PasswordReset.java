package com.ohboon.ohboon.controller.user;


import com.ohboon.ohboon.dao.UserDao;
import com.ohboon.ohboon.utils.ScriptWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
//로그인 페이지에서 PW 분실시 -> PW 재설정
@WebServlet("/user/login/password-reset")
public class PasswordReset extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        req.setAttribute("token", token);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user/password-reset.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String newPassword = req.getParameter("newPW");

        UserDao userDao = new UserDao();
        if (userDao.isTokenValid(token)) {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            userDao.updatePasswordByToken(token, hashedPassword);
            ScriptWriter.alertAndNext(resp, "비밀번호가 성공적으로 변경되었습니다.", "/index/index");
        } else {
            ScriptWriter.alertAndBack(resp, "유효하지 않은 토큰입니다.");
        }
    }
}
