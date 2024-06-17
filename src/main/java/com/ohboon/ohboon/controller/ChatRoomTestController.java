package com.ohboon.ohboon.controller;

import com.ohboon.ohboon.dao.ChatRoomDAO;
import com.ohboon.ohboon.dto.ChatDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet("/chatroomtest")
public class ChatRoomTestController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChatRoomDAO chatRoomDAO = new ChatRoomDAO();
        ChatDTO chatDTO = ChatDTO.builder()
                .boardID(Integer.parseInt(req.getParameter("boardID")))
                .sender(req.getParameter("sender"))
                .chatID(Integer.parseInt(req.getParameter("chatID")))
                .receiver(req.getParameter("receiver"))
                .build();
//req 객체에서 요청 파라미터(boardID, sender, chatID, receiver)를 가져와 ChatDTO 객체를 생성
        int result = chatRoomDAO.CreateChatRoom(chatDTO);
        if (result > 0) {
            req.getRequestDispatcher("/chatroomtest.jsp").forward(req,resp);
        } else {
            resp.sendRedirect("/error");
        }
    }
}
