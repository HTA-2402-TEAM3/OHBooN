package com.ohboon.ohboon.controller;

import com.ohboon.ohboon.dao.ChatRoomDAO;
import com.ohboon.ohboon.dto.ChatDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;

@WebServlet("/list")
public class TestController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // chatDTO
        ChatRoomDAO chatRoomDAO = new ChatRoomDAO();
        List<ChatDTO> chatDTOs= chatRoomDAO.getChatRoom();
        //  가져온 채팅내역을 변수에 저장
        System.out.println(chatDTOs);
        req.setAttribute("chatList", chatDTOs);
        //req객체에 chatList라고 저장
        req.getRequestDispatcher("/list.jsp").forward(req,resp);

    }
}
