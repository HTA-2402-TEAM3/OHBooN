package com.ohboon.ohboon.controller.ChatController;


import com.google.gson.Gson;
import com.ohboon.ohboon.dao.BoardDAO;
import com.ohboon.ohboon.dto.ChatDTO;
import com.ohboon.ohboon.service.MatchService;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

import com.ohboon.ohboon.service.BoardService;
import com.ohboon.ohboon.service.ChatService;


@WebServlet("/makeChat")
public class MakeChatController extends HttpServlet {
    private ChatService chatService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String senderName = (String) httpSession.getAttribute("sessionNickname");
        long board_id;
        long match_id;
        long chat_id;
        String receiverName;
        ChatDTO makeChatDto = null;
        ChatDTO chatRoomDto = null;
        Map<String, Object> reqMap = new HashMap<>();

        if(Objects.equals(req.getParameter("board_id"), "")) {
            receiverName = req.getParameter("receiverName");

            chatRoomDto = ChatDTO.builder()
                    .sender(senderName)
                    .receiver(receiverName)
                    .build();

            chatService = new ChatService();
            chat_id = chatService.getChatId(chatRoomDto);
            System.out.println(chat_id);
        } else {
            board_id = Long.parseLong(req.getParameter("board_id"));

            BoardDAO boardDAO = new BoardDAO();
            receiverName = boardDAO.findEmailByBoardId(board_id);

            makeChatDto = ChatDTO.builder()
                    .boardID(board_id)
                    .sender(senderName)
                    .receiver(receiverName)
                    .build();

            MatchService matchService = new MatchService();
            match_id = matchService.getMatchId(board_id, receiverName, senderName);

            chatService = new ChatService();
            chat_id = chatService.getChatId(makeChatDto);
            System.out.println(chat_id);

            chatService = new ChatService();
            chatService.insertMatchId(match_id, chat_id);

            System.out.println("makeChatDto: "+makeChatDto);

            chatRoomDto = ChatDTO.builder()
                    .chatID(chat_id)
                    .matchID(match_id)
                    .boardID(board_id)
                    .sender(senderName)
                    .receiver(receiverName)
                    .build();
            reqMap.put("match_id", match_id);

            req.setAttribute("match_id", match_id);
        }
        Map<String, Object> chatRoomDtoMap = setChatRoomDto(chatRoomDto);

        reqMap.put("chat_id", chat_id);
        reqMap.put("chatRoomDto", chatRoomDtoMap);

        Gson gson = new Gson();
        String json = gson.toJson(reqMap);
        System.out.println(json);

        req.setAttribute("chatRoomDto", chatRoomDto);
        req.setAttribute("chat_id", chat_id);

        req.getRequestDispatcher("/Test2.jsp").forward(req,resp);

//        resp.sendRedirect("/enterChat?chat_id="+chat_id);
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
