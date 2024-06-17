package com.ohboon.ohboon.controller.ChatController;

import com.ohboon.ohboon.dao.ChatDAO;
import com.ohboon.ohboon.dto.ChatDTO;
import com.ohboon.ohboon.dto.MsgDTO;
import com.ohboon.ohboon.service.ChatService;
import com.ohboon.ohboon.service.MatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/enterChat")
public class ChatController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long chat_id;
        String user_id = (String) req.getSession().getAttribute("sessionNickname");


        if (req.getParameter("chat_id") != null) {
            chat_id = Long.parseLong(req.getParameter("chat_id"));
        } else {
            chat_id = 0;
        }
        ChatService chatService1 = new ChatService();
        List<String> userList = chatService1.findUsersByChatId(chat_id);

        for (String s : userList) {
            if (s.equals(user_id)) {
//                session_id가 채팅방 내의 sender / receiver 둘 중 하나라도 맞으면
//                msgList, match_id data 전달

                ChatService chatService = new ChatService();
                List<MsgDTO> msgList = chatService.getMsgList(chat_id);

                chatService = new ChatService();
//                long match_id = chatService.getMatchIdByChatID(chat_id);

                long match_id = 1;

                ChatDAO chatRoomDAO = new ChatDAO();
                List<ChatDTO> chatList = chatRoomDAO.getChatList(user_id);
//      ChatRoomDAO의 getChatList() 메서드를 호출하여 현재 세션의 ID를 전달

                System.out.println(chatList);

                req.setAttribute("chatList", chatList);
                req.setAttribute("user_id", user_id);
                req.setAttribute("chat_id", chat_id);
                req.setAttribute("match_id", match_id);
                req.setAttribute("msgList", msgList);
                req.getRequestDispatcher("/chatTest.jsp").forward(req, resp);


                System.out.println(user_id);
                return;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
