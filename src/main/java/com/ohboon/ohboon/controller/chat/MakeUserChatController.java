package com.ohboon.ohboon.controller.chat;


import com.google.gson.Gson;
import com.ohboon.ohboon.dto.ChatDTO;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

import com.ohboon.ohboon.service.ChatService;


@WebServlet("/chat/makeUserChat")
public class MakeUserChatController extends HttpServlet {
    private ChatService chatService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String senderName = (String) httpSession.getAttribute("sessionNickname");

        long chat_id;
        String receiverName;
        ChatDTO chatRoomDto = null;
        Map<String, Object> reqMap = new HashMap<>();

        receiverName = req.getParameter("receiverName");

        chatRoomDto = ChatDTO.builder()
                .sender(senderName)
                .receiver(receiverName)
                .build();

        chatService = new ChatService();
        chat_id = chatService.getChatId(chatRoomDto);
        System.out.println(chat_id);

        Map<String, Object> chatRoomDtoMap = setChatRoomDto(chatRoomDto);

        reqMap.put("chat_id", chat_id);
        reqMap.put("chatRoomDto", chatRoomDtoMap);

        Gson gson = new Gson();
        String json = gson.toJson(reqMap);
        System.out.println(json);

        req.setAttribute("chatRoomDto", chatRoomDto);
        req.setAttribute("chat_id", chat_id);

        req.getRequestDispatcher("/Test2.jsp").forward(req, resp);
    }

    private Map<String, Object> setChatRoomDto(ChatDTO chatDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("chat_id", chatDTO.getChatID());
        map.put("match_id", chatDTO.getMatchID());
        map.put("board_id", chatDTO.getBoardID());
        map.put("sender", chatDTO.getSender());
        map.put("receiver", chatDTO.getReceiver());

        return map;
    }
}
