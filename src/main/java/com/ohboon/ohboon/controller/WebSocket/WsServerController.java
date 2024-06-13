package com.ohboon.ohboon.controller.WebSocket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ohboon.ohboon.dto.ChatDTO;
import com.ohboon.ohboon.dto.MsgDTO;
import com.ohboon.ohboon.service.ChatService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import util.WsHttpSessionConfig;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/chat", configurator = WsHttpSessionConfig.class)
public class WsServerController {
    private ChatService chatService;
    private static List<Session> sessionList = new ArrayList<>();
    private static Map<Session, HttpSession> sessionsMap = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        //client가 websocket에 연결되었을 때
        HttpSession httpSession = (HttpSession) config.getUserProperties().get("PRIVATE_HTTP_SESSION");
        sessionsMap.put(session, httpSession);
        sessionList.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session wsSession) {
//        client가 server에 msg 보냈을 때 핸들링
        System.out.println("ws Session( " + wsSession.getId() + " ) : " + message);
        HttpSession httpSession = sessionsMap.get(wsSession);

        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        long chat_id = jsonObject.get("chat_id").getAsLong();
        long match_id = jsonObject.get("match_id").getAsLong();
        String sender = jsonObject.get("sender").getAsString();
        String content = jsonObject.get("content").getAsString();
//        String timestamp = jsonObject.get("timestamp").getAsString();

        saveMsg(chat_id, sender, match_id, content);

        for (Session session1 : sessionList) {
            try {
                session1.getBasicRemote().sendText(sender + ":" + content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
        if(rs > 0) {
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
