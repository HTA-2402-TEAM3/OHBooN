package com.ohboon.ohboon.service;

import com.ohboon.ohboon.dao.ChatDAO;
import com.ohboon.ohboon.dao.MatchDAO;
import com.ohboon.ohboon.dao.MsgDAO;
import com.ohboon.ohboon.dto.ChatDTO;
import com.ohboon.ohboon.dto.MsgDTO;
import org.eclipse.tags.shaded.org.apache.xalan.xsltc.compiler.util.MultiHashtable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void insertMatchId(long matchId, long chatId) {
        Map<String, Long> map = new HashMap<>();
        map.put("match_id", matchId);
        map.put("chat_id", chatId);

        ChatDAO chatDAO1 = new ChatDAO();
        int rs = chatDAO1.insertMatchId(map);
    }

    public Map<Long, Map<String, Object>> getMsgMap(long chatId) {
        MsgDAO msgDAO = new MsgDAO();
        List<MsgDTO> list = msgDAO.getMsgList(chatId);

        Map<String, Object> msgDtoMap = new HashMap<>();

        Map<Long, Map<String, Object>> MsgMap = new HashMap<>();
        for (MsgDTO msgDTO : list) {
            msgDtoMap.put("chatroom_id", msgDTO.getChatRoomId());
            msgDtoMap.put("match_id", msgDTO.getMatchId());
            msgDtoMap.put("sender", msgDTO.getSender());
            msgDtoMap.put("content", msgDTO.getContent());
            msgDtoMap.put("time_stamp", msgDTO.getTimeStamp());
            MsgMap.put(msgDTO.getMessageId(), msgDtoMap);
        }
        return MsgMap;
    }
}
