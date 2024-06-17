package com.ohboon.ohboon.controller;

import com.ohboon.ohboon.dao.MsgDAO;
import com.ohboon.ohboon.dto.MsgDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;

// DB에서 msgDTO를 가져 왔어
//
@WebServlet("/view")
public class ViewChat extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int msgChatID = 0;

        if (req.getParameter("msgChatID") !=null) {
            msgChatID = Integer.parseInt(req.getParameter("msgChatID"));
        }
        MsgDAO msgDAO = new MsgDAO();
        List<MsgDTO> msgList = msgDAO.getMsgList(msgChatID);
        req.setAttribute("msgList",msgList); // jsp페이지에서 "설정해둔" 이름을 불러올수있음
        req.getRequestDispatcher("/chatroomtest.jsp").forward(req, resp);
    }
}
