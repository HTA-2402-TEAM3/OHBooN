package com.ohboon.ohboon.controller.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ohboon.ohboon.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/admin/updatePrivateField")
public class UpdatePrivateField extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String email = request.getParameter("email");
        //boolean privateField = Boolean.parseBoolean(request.getParameter("privateField"));

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String requestBody = sb.toString();
        System.out.println("Request Body: " + requestBody); // 추가된 로그

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);
        String email = jsonObject.get("email").getAsString();
        boolean privateField = jsonObject.get("privateField").getAsBoolean();

        // 디버깅 로그
        System.out.println("Email: " + email);
        System.out.println("PrivateField: " + privateField);

        response.setContentType("application/json; charset=UTF-8");
        UserDAO userDAO = new UserDAO();
        Map<String, String> resultMap = new HashMap<>();

        try {
            int result = userDAO.updatePrivateField(email, privateField);
            if (result>0) {
                resultMap.put("status", "success");
                resultMap.put("message", "프로필 공개 여부가 성공적으로 업데이트되었습니다.");
            }
            else {
                resultMap.put("status", "fail");
                resultMap.put("message", "업데이트 실패");
            }
        } catch (Exception e) {
            e.printStackTrace(); // 디버깅 로그
            resultMap.put("status", "error");
            resultMap.put("message", "프로필 공개 여부 업데이트 중 오류가 발생했습니다.");
        }
        response.getWriter().write(new Gson().toJson(resultMap));
    }
}
