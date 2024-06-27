package com.ohboon.ohboon.controller.admin;

import com.ohboon.ohboon.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/updateUserGrade")
public class UpdateUserGrade extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String grade = request.getParameter("grade");

        if (email == null || grade == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid grade value");
            return;
        }

        UserDAO userDAO = new UserDAO();
        try {
            userDAO.updateUserGrade(email, grade);
            response.sendRedirect(request.getContextPath() + "/admin/userList");
        } catch (Exception e) {
            throw new ServletException("Error updating user grade", e);
        }
    }
}
