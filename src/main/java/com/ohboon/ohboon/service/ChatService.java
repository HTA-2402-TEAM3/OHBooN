package com.ohboon.ohboon.service;

import com.ohboon.ohboon.dao.ChatDAO;
import com.ohboon.ohboon.dao.MatchDAO;
import com.ohboon.ohboon.dao.MsgDAO;
import com.ohboon.ohboon.dto.ChatDTO;
import com.ohboon.ohboon.dto.MsgDTO;

public class ChatService {

    public int saveMsg(MsgDTO msgDTO) {
        MsgDAO msgDAO = new MsgDAO();
        return msgDAO.saveMsg(msgDTO);
    }

    public long getChatId(ChatDTO chatRoomDto) {
        ChatDAO chatDAO = new ChatDAO();
        return chatDAO.CreateChatRoom(chatRoomDto);
    }
}
