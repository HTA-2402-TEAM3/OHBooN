package com.ohboon.ohboon.controller.admin;

import com.ohboon.ohboon.dao.UserDAO;
import com.ohboon.ohboon.mybatis.MybatisConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/admin/updateUserInfo")
public class UpdateUserInfo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (SqlSession session = MybatisConnectionFactory.getSqlSession()) {
            String email = request.getParameter("email");
            Map<String, Object> paramMap = new HashMap<>();

            paramMap.put("email", email);
            paramMap.put("birth", request.getParameter("birth_" + email));
            paramMap.put("phone", request.getParameter("phone_" + email));
            paramMap.put("available", Boolean.parseBoolean(request.getParameter("available_" + email)));
            paramMap.put("privateField", Boolean.parseBoolean(request.getParameter("privateField_" + email)));

            // 관리자만 수정 가능한 필드
            if (request.isUserInRole("ADMIN")) {
                paramMap.put("userName", request.getParameter("userName_" + email));
            }

            session.update("updateUserInfo", paramMap);
            session.commit();

            response.sendRedirect(request.getContextPath() + "/admin/userList");
        } catch (Exception e) {
            throw new ServletException("Error updating user info", e);
        }
    }
}
