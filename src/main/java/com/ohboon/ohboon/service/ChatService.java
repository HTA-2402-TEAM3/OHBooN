package com.ohboon.ohboon.service;

import com.ohboon.ohboon.dao.ChatDAO;
import com.ohboon.ohboon.dao.MatchDAO;
import com.ohboon.ohboon.dao.MsgDAO;
import com.ohboon.ohboon.dto.ChatDTO;
import com.ohboon.ohboon.dto.MsgDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatService {
    ChatDAO chatDAO;

    public int saveMsg(MsgDTO msgDTO) {
        MsgDAO msgDAO = new MsgDAO();
        return msgDAO.saveMsg(msgDTO);
    }

    public List<MsgDTO> getMsgList(long chat_id) {
        MsgDAO msgDAO = new MsgDAO();
        return msgDAO.getMsgList(chat_id);
    }
    public long getChatId(ChatDTO chatRoomDto) {
        ChatDAO chatDAO = new ChatDAO();
        return chatDAO.CreateChatRoom(chatRoomDto);
    }

    public List<String> findUsersByChatId(long chatId) {
        chatDAO = new ChatDAO();
        List<HashMap<String, String>> results = chatDAO.findUsersByChatId(chatId);
        List<String> users = new ArrayList<>();

        for(HashMap<String, String> result : results) {
            users.add(result.get("sender"));
            users.add(result.get("receiver"));
        }

        return users;
    }

}
