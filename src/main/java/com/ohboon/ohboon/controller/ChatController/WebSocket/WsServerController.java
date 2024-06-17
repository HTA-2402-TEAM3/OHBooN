package com.ohboon.ohboon.controller.ChatController.WebSocket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ohboon.ohboon.dto.MsgDTO;
import com.ohboon.ohboon.service.ChatService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@ServerEndpoint(value = "/chat", configurator = WsHttpSessionConfig.class)
public class WsServerController {
    private ChatService chatService;
    private static List<Session> sessionList = new ArrayList<>();
//    ws 연결된 모든 session이 들어감
    private static Map<Session, String> sessionsMap = new HashMap<>();
//    ws 연결된 wsSession으로 httpSession받아오는 용도 --> 닉네임 받아올거임
    private static Map<Long, List<Session>> chatRoomSessionMap = Collections.synchronizedMap(new HashMap<>());
//    chat_id(key) 채팅방에 저장된 user들 websocket(value) map

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        //client가 websocket에 연결되었을 때
        HttpSession httpSession = (HttpSession) config.getUserProperties().get("PRIVATE_HTTP_SESSION");
//        String userNickname = (String) config.getUserProperties().get("PRIVATE_HTTP_SESSION");
        String userNickname = (String) httpSession.getAttribute("sessionNickname");

        sessionsMap.put(session, userNickname);
        sessionList.add(session);

        System.out.println(sessionList);
    }

    @OnMessage
    public void onMessage(String message, Session wsSession) {
//        client가 server에 msg 보냈을 때 핸들링
        System.out.println("ws Session( " + wsSession.getId() + " ) : " + message);

        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        long chat_id = jsonObject.get("chat_id").getAsLong();
        long match_id = jsonObject.get("match_id").getAsLong();
        String sender = jsonObject.get("user_id").getAsString();
        String content = jsonObject.get("content").getAsString();

        System.out.println(sender);

        chatRoomSessionMap = setChatUsers(chat_id, sessionsMap);
        System.out.println("chatRoomSessionMap : "+chatRoomSessionMap);
        saveMsg(chat_id, sender, match_id, content);

        chatRoomSessionMap.get(chat_id).forEach(session1 -> {
            try {
                if (session1 == wsSession) {
                    session1.getBasicRemote().sendText("나 :" + content);
                } else {
                    session1.getBasicRemote().sendText(sender + " : " + content);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Map<Long, List<Session>> setChatUsers(long chatId, Map<Session, String> sessionsMap) {
        List<Session> userList = new ArrayList<>();

        chatService = new ChatService();
        List<String> users = chatService.findUsersByChatId(chatId);

        System.out.println("users :"+users);
        System.out.println("sessionsMap :" +sessionsMap);

        for(Session key : sessionsMap.keySet()) {
            for(String user : users) {
                if(sessionsMap.get(key).equals(user)) {
                    userList.add(key);
                }
            }
        }
        chatRoomSessionMap.put(chatId, userList);
        System.out.println("userList : "+userList);
        System.out.println("chatRoomSessionMap : "+chatRoomSessionMap);
        return chatRoomSessionMap;
    }


    private void saveMsg(long chatId, String sender, long match_id, String content) {
        MsgDTO msgDTO = MsgDTO.builder()
                .chatRoomId(chatId)
                .matchId(match_id)
                .sender(sender)
                .content(content)
                .timeStamp(LocalDateTime.now())
                .build();
        System.out.println(msgDTO);

        chatService = new ChatService();
        int rs = chatService.saveMsg(msgDTO);
        if (rs > 0) {
            System.out.println(msgDTO);
        }
    }

    @OnClose
    public void onClose(Session session) {
//        클라이언트가 ws서버 연결이 끊겼을 때 wsSession 출력
        System.out.println("client is now disconnected... : " + session.getId());
    }

    @OnError
    public void onError(Throwable t, Session session) {
//        error
        System.out.println("Error!! : " + t.getMessage());
    }
}
