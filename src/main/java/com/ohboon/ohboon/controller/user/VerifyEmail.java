package com.ohboon.ohboon.controller.user;

import com.ohboon.ohboon.dao.UserDAO;
import com.ohboon.ohboon.utils.ScriptWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//회원가입 후 이메일 인증시 사용
@WebServlet("/user/verify-email")
public class VerifyEmail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String token = req.getParameter("token");

        // 로그 추가
        System.out.println("Received email: " + email + ", token: " + token);

        if (email == null || token == null) {
            ScriptWriter.alertAndBack(resp, "유효하지 않은 인증 링크입니다.");
            return;
        }

        UserDAO userDao = new UserDAO();
        int result = userDao.verifyEmail(email, token);
        String grade = userDao.gradeCheck(email);

        if(grade.equals("MEMBER")){
            ScriptWriter.alertAndNext(resp, "이미 인증된 이메일입니다.", "../index/index");
        }
        else if (result > 0) {
            ScriptWriter.alertAndNext(resp, "이메일 인증이 완료되었습니다. 로그인 해주세요.", "../index/index");
        } else {
            ScriptWriter.alertAndBack(resp, "유효하지 않은 인증 링크입니다.");
        }
    }
}
