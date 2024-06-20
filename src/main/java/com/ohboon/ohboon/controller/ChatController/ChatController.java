package com.ohboon.ohboon.controller.ChatController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ohboon.ohboon.dao.ChatDAO;
import com.ohboon.ohboon.dao.MatchDAO;
import com.ohboon.ohboon.dto.ChatDTO;
import com.ohboon.ohboon.dto.MatchDTO;
import com.ohboon.ohboon.dto.MsgDTO;
import com.ohboon.ohboon.service.ChatService;
import com.ohboon.ohboon.service.MatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.LocalDateTimeAdapter;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/enterChat")
public class ChatController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long chat_id;
        String user_id = (String) req.getSession().getAttribute("sessionNickname");
        System.out.println(user_id);


        if (req.getParameter("chat_id") != null) {
            chat_id = Long.parseLong(req.getParameter("chat_id"));
        } else {
            chat_id = 0;
        }
        ChatService chatService1 = new ChatService();
        List<String> userList = chatService1.findUsersByChatId(chat_id);

        for (String s : userList) {
            if (s.equals(user_id)) {

//                session_id가 채팅방 내의 sender, receiver 둘 중 하나라도 맞으면
//                msgList, match_id data 전달

                ChatService chatService = new ChatService();
//                List<MsgDTO> msgList = chatService.getMsgList(chat_id);
                Map<LocalDateTime, Map<String, Object>> msgMap = chatService.getMsgMap(chat_id);

                ChatDAO chatRoomDAO = new ChatDAO();
                String match_email = "null";
                long match_id = chatRoomDAO.getMatchIdByChatId(chat_id);
                if (match_id != 0) {
                    MatchDAO matchDAO = new MatchDAO();
                    match_email = matchDAO.getMatchEmail(match_id);
                }
                Map<String, Object> reqMap = reqMapSet(match_email, user_id, chat_id, match_id, msgMap);


                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
                Gson gson = gsonBuilder.create();
                String json = gson.toJson(reqMap);

                resp.setContentType("application/json");
                resp.setCharacterEncoding("utf-8");
                PrintWriter out = resp.getWriter();
                out.println(json);
                return;
            }
        }
    }

    private Map<String, Object> reqMapSet(String matchEmail, String userId, long chatId, long matchId, Map<LocalDateTime, Map<String, Object>> msgMap) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("match_email", matchEmail);
        reqMap.put("user_id", userId);
        reqMap.put("chat_id", chatId);
        reqMap.put("match_id", matchId);
        reqMap.put("msgMap", msgMap);

        return reqMap;
    }
}
