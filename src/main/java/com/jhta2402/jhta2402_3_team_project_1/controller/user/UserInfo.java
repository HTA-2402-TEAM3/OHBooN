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

@WebServlet("/user/info")
public class UserInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String nickname = (String) session.getAttribute("sessionNickname");


        UserDao userDao = new UserDao();
        UserDto infoUserDto = userDao.infoUser(nickname);

        if (infoUserDto == null) {
            ScriptWriter.alertAndBack(resp,"오류: 사용자 정보를 가져올 수 없음");

        } else {
            req.setAttribute("infoUserDto", infoUserDto);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user/info.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
