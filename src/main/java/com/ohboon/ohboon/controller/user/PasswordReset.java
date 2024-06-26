package com.ohboon.ohboon.controller.user;


import com.ohboon.ohboon.dao.UserDAO;
import com.ohboon.ohboon.utils.ScriptWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Map;

//로그인 페이지에서 PW 분실시 -> PW 재설정
@WebServlet("/user/login/password-reset")
public class PasswordReset extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user/password-reset.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String token = req.getParameter("token");
        String newPassword = req.getParameter("newPW");

        UserDAO userDao = new UserDAO();

        String email = userDao.getEmailByToken(token);

        // 토큰과 이메일 확인
        boolean isValid = userDao.verifyPasswordResetToken(email, token);

        if (email != null) {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            int result = userDao.updatePassword(Map.of("password", hashedPassword, "email", email, "token", token));

            if (result > 0) {
                ScriptWriter.alertAndNext(resp, "비밀번호가 성공적으로 변경되었습니다.", "/user/login");
            } else {
                ScriptWriter.alertAndBack(resp, "비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
            }
        }else {
            ScriptWriter.alertAndBack(resp, "유효하지 않은 토큰입니다. 다시 시도해주세요.");
        }

    }
}