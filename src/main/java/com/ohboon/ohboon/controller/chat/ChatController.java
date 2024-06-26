package com.ohboon.ohboon.controller.chat;


import com.ohboon.ohboon.dao.ChatDAO;
import com.ohboon.ohboon.dto.ChatDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;


@WebServlet("/chat")
public class ChatController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ss = req.getSession();
        String userID = (String) ss.getAttribute("sessionNickname");

        ChatDAO chatRoomDAO = new ChatDAO();
        List<ChatDTO> chatList = chatRoomDAO.getChatList(userID);

        System.out.println("chatList: "+chatList);
        req.getRequestDispatcher("/WEB-INF/chatTest/Test2.jsp").forward(req,resp);
    }
}
