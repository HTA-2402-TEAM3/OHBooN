package com.ohboon.ohboon.controller;


import com.ohboon.ohboon.dao.ChatDAO;
import com.ohboon.ohboon.dto.ChatDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Mapper;

import java.io.IOException;
import java.util.List;


@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    @Mapper
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userID = req.getParameter("user_id");
        HttpSession ss = req.getSession();

        ChatDAO chatRoomDAO = new ChatDAO();
        List<ChatDTO> chatList = chatRoomDAO.getChatList(userID);
//      ChatRoomDAO의 getChatList() 메서드를 호출하여 현재 세션의 ID를 전달

        System.out.println(chatList);

        req.setAttribute("chatList", chatList);

        ss.setAttribute("sessionNickname", userID);
//        resp.sendRedirect("../chatList.jsp");

        req.getRequestDispatcher("chatList.jsp").forward(req,resp);
    }
}
