package com.ohboon.ohboon.controller;

import com.ohboon.ohboon.dao.ChatRoomDAO;
import com.ohboon.ohboon.dto.ChatDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;

@WebServlet("/test") // 원래 test -> list
public class ListChat extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
//      세션으로 ID를 가져옴
//        ChatDTO chatDTO = new ChatDTO();
//        chatDTO.setSender(sessionId); // 세션 ID 설정

        ChatRoomDAO chatRoomDAO = new ChatRoomDAO();
        List<ChatDTO> chatList = chatRoomDAO.getChatList(sessionId);
//      ChatRoomDAO의 getChatList() 메서드를 호출하여 현재 세션의 ID를 전달
        req.setAttribute("chatList",chatList);
        RequestDispatcher dispatcher =
                req.getRequestDispatcher("/list.jsp");
        dispatcher.forward(req,resp);
        // 이거는 새로운 페이지를 열어
    }
}

