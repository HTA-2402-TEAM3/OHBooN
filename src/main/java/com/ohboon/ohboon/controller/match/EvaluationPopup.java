package com.ohboon.ohboon.controller.match;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ohboon.ohboon.dto.MatchDTO;
import com.ohboon.ohboon.service.MatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/pop-up")
public class EvaluationPopup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ss = req.getSession();
        String userEmail = (String) ss.getAttribute("sessionEmail");
        System.out.println(userEmail);
        if (userEmail != null && !userEmail.equals("")) {
            MatchService matchService = new MatchService();
            List<MatchDTO> evaluationMap = matchService.matchEvaluation(userEmail);
//                로그인 성공하면 매치 테이블 조회 후 평가하기 알림 보냄
            System.out.println("evaluationMap in login : " + evaluationMap);
            if(evaluationMap.isEmpty()) {
                System.out.println("empty");
                req.setAttribute("evalEmpty",null);
            }
            req.setAttribute("evaluationMap", evaluationMap);
            req.getRequestDispatcher("/WEB-INF/chatTest/evaluation-popup.jsp").forward(req, resp);
        }
        else {
            resp.sendRedirect("/WEB-INF/index/index.jsp");
        }
    }
}
