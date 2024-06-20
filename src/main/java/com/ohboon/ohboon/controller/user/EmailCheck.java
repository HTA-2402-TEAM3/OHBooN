package com.ohboon.ohboon.controller.user;

import com.google.gson.Gson;
import com.ohboon.ohboon.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//회원가입시 이메일 중복체크
@WebServlet("/user/email-check")
public class EmailCheck extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        System.out.println(email);
        UserDao userDao = new UserDao();
        int result = userDao.emailCheck(email);

        // 결과값을 json으로 변경해서 프론트에 전달해야 함.
        Gson gson = new Gson();
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("count",result);
        String resultJson = gson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }
}
