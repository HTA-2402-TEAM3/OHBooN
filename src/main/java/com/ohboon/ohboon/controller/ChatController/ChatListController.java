package com.ohboon.ohboon.controller.ChatController;


import com.google.gson.Gson;
import com.ohboon.ohboon.service.ChatService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet("/chatList")
public class ChatListController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String userID = (String) httpSession.getAttribute("sessionNickname");

        ChatService chatService = new ChatService();
        Map<Long, Map<String, Object>> reqMap = chatService.getChatList(userID);

        Gson gson = new Gson();
        String json = gson.toJson(reqMap);
        System.out.println("json: "+json);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        out.println(json);
    }
}
