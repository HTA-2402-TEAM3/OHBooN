package com.ohboon.ohboon.controller.chat;

import com.google.gson.Gson;
import com.ohboon.ohboon.dao.BoardDAO;
import com.ohboon.ohboon.dao.UserDao;
import com.ohboon.ohboon.dto.ChatDTO;
import com.ohboon.ohboon.dto.ModalDto;
import com.ohboon.ohboon.service.ChatService;
import com.ohboon.ohboon.service.MatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/makeBoardChat")
public class MakeBoardChatController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String senderName = (String) httpSession.getAttribute("sessionNickname");

        long board_id = Long.parseLong(req.getParameter("boardID"));
        System.out.println(board_id);

        Map<String, Object> reqMap = new HashMap<>();

        BoardDAO boardDAO = new BoardDAO();
        String boardWriterName = boardDAO.findEmailByBoardId(board_id);
        UserDao userDao = new UserDao();
        String receiverName = userDao.findNicknameByEmail(boardWriterName);

        ChatDTO makeChatDto = ChatDTO.builder()
                .boardID(board_id)
                .sender(senderName)
                .receiver(receiverName)
                .build();
        ChatService chatService = new ChatService();
        long chat_id = chatService.getChatId(makeChatDto);

        System.out.println(chat_id);

        if (chat_id != 0) {
            MatchService matchService = new MatchService();
            long match_id = matchService.getMatchId(board_id, boardWriterName, senderName);


            chatService = new ChatService();
            chatService.insertMatchId(match_id, chat_id);

            System.out.println("makeChatDto: " + makeChatDto);

            ChatDTO chatRoomDto = ChatDTO.builder()
                    .chatID(chat_id)
                    .matchID(match_id)
                    .boardID(board_id)
                    .sender(senderName)
                    .receiver(receiverName)
                    .build();

            req.setAttribute("match_id", match_id);

            Map<String, Object> chatRoomDtoMap = setChatRoomDto(chatRoomDto);

            reqMap.put("match_id", match_id);
            reqMap.put("chat_id", chat_id);
            reqMap.put("chatRoomDto", chatRoomDtoMap);

            Gson gson = new Gson();
            String json = gson.toJson(reqMap);
            System.out.println(json);

            req.setAttribute("chatRoomDto", chatRoomDto);
            req.setAttribute("chat_id", chat_id);

            req.getRequestDispatcher("/WEB-INF/chatTest/Test2.jsp").forward(req, resp);
        } else {
            ModalDto modalDto = new ModalDto("채팅","이미 생성된 대화가 있습니다.","show");
            HttpSession session02 = req.getSession(); // 모달을 session02에 싣기
            session02.setAttribute("modal", modalDto);
            resp.sendRedirect("../index/index");
        }
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





