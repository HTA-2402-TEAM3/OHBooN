package com.ohboon.ohboon.controller.admin;

import com.ohboon.ohboon.dao.UserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/admin/updateAvailability")
public class UpdateAvailability extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();

        BufferedReader reader = request.getReader();
        JsonObject jsonRequest = gson.fromJson(reader, JsonObject.class);

        String email = jsonRequest.get("email").getAsString();
        boolean available = jsonRequest.get("available").getAsBoolean();

        UserDAO userDAO = new UserDAO();
        JsonObject jsonResponse = new JsonObject();

        try {
            int result = userDAO.updateAvailability(email, available);
            if (result > 0) {
                jsonResponse.addProperty("status", "success");
                jsonResponse.addProperty("message", "계정 상태가 성공적으로 업데이트되었습니다.");
            } else {
                jsonResponse.addProperty("status", "fail");
                jsonResponse.addProperty("message", "업데이트 실패");
            }
        } catch (Exception e) {
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "계정 상태 업데이트 중 오류가 발생했습니다.");
        }

        response.getWriter().write(gson.toJson(jsonResponse));
    }
}
