package com.ohboon.ohboon.controller.match;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ohboon.ohboon.service.MatchService;
import com.ohboon.ohboon.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/evaluation")
public class EvaluationController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        List<Map<String, String>> evalList = new ArrayList<>();
        try {
        TypeToken<List<Map<String, String>>> token = new TypeToken<List<Map<String, String>>>(){};
        Gson gson1 = new Gson();
        List<Map<String, String>> maps = gson1.fromJson(reader, token);

        for (Map<String, String> map : maps) {
            System.out.println(map);
            evalList.add(map);
        }
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"message\":\"Success\"}");

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\":\"Internal Server Error\"}");
        }
        UserService userService = new UserService();
        userService.setEvaluation(evalList);
    }
}
