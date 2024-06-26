package com.ohboon.ohboon.controller.chat.WebSocket;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import jakarta.websocket.server.ServerEndpointConfig.Configurator;

public class WsHttpSessionConfig extends Configurator {
//    httpSession(로그인 한 아이디)을 가져와 WsServer에서 핸들링 하기 위한 클래스
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        if(httpSession!=null) {
            String userNickname =(String) httpSession.getAttribute("sessionNickname");
            sec.getUserProperties().put("PRIVATE_HTTP_SESSION",userNickname);
//            httpSession값 저장
        }
    }
}
