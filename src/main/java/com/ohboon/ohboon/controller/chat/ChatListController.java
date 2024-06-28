package com.ohboon.ohboon.controller.chat;


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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/chat/chatList")
public class ChatListController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String userID = (String) httpSession.getAttribute("sessionNickname");

        ChatService chatService = new ChatService();
        Map<Long, Map<String, Object>> reqMap = chatService.getChatList(userID);

        List<Map<String, Object>> list = new ArrayList<>();
        reqMap.forEach((key, value) -> {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("key", key);
            entry.put("value", value);
            list.add(entry);
        });
        Gson gson = new Gson();
        String json = gson.toJson(list);


        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        out.println(json);
    }
}
