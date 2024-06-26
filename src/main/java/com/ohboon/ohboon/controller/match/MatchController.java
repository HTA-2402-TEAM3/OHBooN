package com.ohboon.ohboon.controller.match;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ohboon.ohboon.dao.BoardDAO;
import com.ohboon.ohboon.dao.MatchDAO;
import com.ohboon.ohboon.service.MatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/match")
public class MatchController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long match_id = Long.parseLong(req.getParameter("match_id"));
        System.out.println(match_id);

        MatchService matchService = new MatchService();
        long board_id = matchService.isMatched(match_id);
//        isMatched 변경 - 1 (매치완료)

        if(board_id > 0) {
            MatchService matchService1 = new MatchService();
            matchService1.matchBoard(board_id);
        }

        Gson gson = new Gson();
        Map<String, Integer> matchMap = new HashMap<>();
        matchMap.put("isMatch", 1);
        String json = gson.toJson(matchMap);

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(json);
    }
}
