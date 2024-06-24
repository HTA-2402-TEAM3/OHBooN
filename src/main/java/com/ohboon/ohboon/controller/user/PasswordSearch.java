package com.ohboon.ohboon.controller.user;

import com.ohboon.ohboon.dao.UserDao;
import com.ohboon.ohboon.utils.ScriptWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.UUID;
//로그인 페이지에서 PW 분실시 사용
@WebServlet("/user/login/password-search")
public class PasswordSearch extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher= req.getRequestDispatcher("/WEB-INF/user/password-search.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        UserDao userDao = new UserDao();

        // 방법1) 직접적으로 PW 변경

        /*
        // 무작위 비밀번호 생성
        String newPassword = PasswordGenerator.generateRandomPassword();

        // DB에서 사용자 PW를 변경
        int result = userDao.updatePassword(email, newPassword);

        if (result > 0) {
            Map<String, String> sendMailInfo = new HashMap<>();
            sendMailInfo.put("from", "your-naver-email@naver.com");
            sendMailInfo.put("to", email);
            sendMailInfo.put("subject", "Your New Password");
            sendMailInfo.put("content", "Your new password is: " + newPassword);
            sendMailInfo.put("format", "text/html; charset=UTF-8");

            try {
                NaverMail naverMail = new NaverMail();
                naverMail.sendMail(sendMailInfo);
                ScriptWriter.alertAndNext(resp, "이메일로 새로운 비밀번호를 발송하였습니다.", "../index/index");
            } catch (MessagingException e) {
                ScriptWriter.alertAndBack(resp, "이메일 발송에 실패하였습니다. 다시 시도해주세요.");
                e.printStackTrace();
            }
        } else {
            ScriptWriter.alertAndBack(resp, "PW 변경에 실패하였습니다. 다시 시도해주세요.");
        }

         */

        // 방법2) PW변경 창 링크를 보내고 PW변경 창에서 PW 수정
        // 토큰 생성
        String token = UUID.randomUUID().toString();

        // 토큰 저장 (예: 데이터베이스에 저장)
        boolean saveTokenResult = userDao.savePasswordResetToken(email, token);

        if (saveTokenResult) {
            // 비밀번호 변경 링크 생성
            String resetLink = "http://localhost:8080/user/login/password-reset?token=" + token + "&email=" + email;

            // 비밀번호 변경 링크를 이메일로 전송
            boolean emailSent = userDao.sendPasswordByEmail(email, resetLink);

            if (emailSent) {
                ScriptWriter.alertAndNext(resp, "비밀번호 변경 링크가 이메일로 전송되었습니다.", "/index/index");
            } else {
                ScriptWriter.alertAndBack(resp, "이메일 발송에 실패하였습니다. 다시 시도해주세요.");
            }
        } else {
            ScriptWriter.alertAndBack(resp, "토큰 저장에 실패하였습니다. 다시 시도해주세요.");
        }
    }
}
