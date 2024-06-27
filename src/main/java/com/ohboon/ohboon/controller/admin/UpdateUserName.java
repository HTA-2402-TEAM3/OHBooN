package com.ohboon.ohboon.controller.admin;

import com.ohboon.ohboon.dao.UserDAO;
import com.ohboon.ohboon.utils.ScriptWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/updateUserName")
public class UpdateUserName extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String newUserName  = request.getParameter("userName");

        response.setContentType("text/html;charset=UTF-8");

        UserDAO userDAO = new UserDAO();

        try {
            int result = userDAO.updateUserName(email, newUserName);
            if (result>0) ScriptWriter.alertAndNext(response, "사용자 이름이 성공적으로 업데이트되었습니다.", "/admin/userList");
            else ScriptWriter.alertAndNext(response, "이름 변경 실패", "/admin/userList");
        } catch (Exception e) {
            ScriptWriter.alertAndNext(response, "사용자 이름 업데이트 중 오류가 발생했습니다.", "/admin/userList");
        }
    }
}
