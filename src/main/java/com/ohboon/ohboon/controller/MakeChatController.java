package com.ohboon.ohboon.controller;


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
import java.util.ArrayList;
import java.util.List;

import com.ohboon.ohboon.service.BoardService;
import com.ohboon.ohboon.service.ChatService;


@WebServlet("/makeChat")
public class MakeChatController extends HttpServlet {
    private ChatService chatService;
    private BoardService boardService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
//        String senderName = (String) httpSession.getAttribute("sessionNickname");
        String senderName = httpSession.getId();
        long board_id = Long.parseLong(req.getParameter("board_id"));

        BoardDAO boardDAO = new BoardDAO();
        String receiverName = boardDAO.findEmailByBoardId(board_id);
//        이거 바꿔달라고 하기 nickname!!

        ChatDTO chatRoomDto = ChatDTO.builder()
                .boardID(board_id)
                .sender(senderName)
                .receiver(receiverName)
                .build();

// 채팅방 목록도 받아올겨
        chatService = new ChatService();
        long chat_id = chatService.getChatId(chatRoomDto);
//        chatroom table 생성하고 생성된 chat_id 가져오기
        System.out.println(chat_id);

        MatchService matchService = new MatchService();
        long match_id = matchService.getMatchId(board_id, receiverName);
//        match table 생성하고 생성된 match_id 가져오기
        System.out.println(match_id);

        List<ChatDTO> chatRoomList = new ArrayList<>();
        chatRoomList.add(chatRoomDto);

        req.setAttribute("chatRoomDto", chatRoomDto);
        req.setAttribute("chatRoomList", chatRoomList);
        req.setAttribute("match_id", match_id);
        req.setAttribute("chat_id", chat_id);

        req.getRequestDispatcher("/chatTest.jsp").forward(req,resp);
    }
}
