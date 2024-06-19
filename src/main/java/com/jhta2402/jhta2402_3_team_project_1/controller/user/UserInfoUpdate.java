package com.jhta2402.jhta2402_3_team_project_1.controller.user;

import com.jhta2402.jhta2402_3_team_project_1.dao.UserDao;
import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
import com.jhta2402.jhta2402_3_team_project_1.utils.ScriptWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/user/info/update")
public class UserInfoUpdate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Boolean passwordVerified = (Boolean) session.getAttribute("passwordVerified");

        if (passwordVerified == null || !passwordVerified) {
            ScriptWriter.alertAndBack(resp, "비밀번호 확인이 필요합니다.");
            return;
        }

        // ■ 수정할 사항 정하기
        String email = (String) session.getAttribute("sessionEmail");
        String nickname = req.getParameter("nickname");
        String phone = req.getParameter("phone");

        UserDao userDao = new UserDao();
        //int result = userDao.updateUserInfo(email, nickname, phone);
        int result=0; // 위의 코드 수정 필요

        if (result > 0) {
            session.setAttribute("sessionNickname", nickname);
            session.setAttribute("sessionPhone", phone);
            ScriptWriter.alertAndNext(resp, "회원정보가 수정되었습니다.", "../index/index");
        } else {
            ScriptWriter.alertAndBack(resp, "회원정보 수정에 실패하였습니다.");
        }
    }
}
