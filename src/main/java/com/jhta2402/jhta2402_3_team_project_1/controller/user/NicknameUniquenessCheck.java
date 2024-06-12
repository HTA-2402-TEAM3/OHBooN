package com.jhta2402.jhta2402_3_team_project_1.controller.user;

import com.google.gson.Gson;
import com.jhta2402.jhta2402_3_team_project_1.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/nickname-uniqueness-check")
public class NicknameUniquenessCheck extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("nickname");
        UserDao userDao = new UserDao();
        int result = userDao.idCheck(nickname);

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
