package com.jhta2402.jhta2402_3_team_project_1.controller.admin;


import com.jhta2402.jhta2402_3_team_project_1.dao.UserDao;
import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/home")
public class AdminHome extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        UserDao userDao = new UserDao();
        UserDto infoUserDto = userDao.infoUser(email);
        System.out.println(infoUserDto.toString());
        req.setAttribute("infoMemberDto", infoUserDto);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/admin/admin-home.jsp");
        dispatcher.forward(req, resp);

    }
}
