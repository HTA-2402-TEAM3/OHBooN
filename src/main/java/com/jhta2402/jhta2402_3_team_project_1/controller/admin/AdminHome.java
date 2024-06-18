package com.jhta2402.jhta2402_3_team_project_1.controller.admin;


import com.jhta2402.jhta2402_3_team_project_1.dao.UserDao;
import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
import com.jhta2402.jhta2402_3_team_project_1.utils.ScriptWriter;
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
        String grade = req.getParameter("grade");

        if(grade.equals("MANAGER") || grade.equals("ADMIN")){
            UserDao userDao = new UserDao();
            UserDto adminUserDto = userDao.adminUser(grade);
            System.out.println(adminUserDto.toString());
            req.setAttribute("adminUserDto", adminUserDto);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/admin/admin-home.jsp");
            dispatcher.forward(req, resp);
        }else{
            ScriptWriter.alertAndBack(resp, "잘못된 접근입니다.");
        }
    }
}
