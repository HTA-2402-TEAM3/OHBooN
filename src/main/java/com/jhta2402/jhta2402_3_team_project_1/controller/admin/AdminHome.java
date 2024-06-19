package com.jhta2402.jhta2402_3_team_project_1.controller.admin;


import com.jhta2402.jhta2402_3_team_project_1.dao.UserDao;
import com.jhta2402.jhta2402_3_team_project_1.dto.Grade;
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

@WebServlet("/admin/home")
public class AdminHome extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/admin/admin-home.jsp");
        dispatcher.forward(req, resp);
    }
}
