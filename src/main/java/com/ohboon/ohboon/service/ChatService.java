package com.ohboon.ohboon.service;

import com.ohboon.ohboon.dao.ChatDAO;
import com.ohboon.ohboon.dao.MatchDAO;
import com.ohboon.ohboon.dao.MsgDAO;
import com.ohboon.ohboon.dao.UserDao;
import com.ohboon.ohboon.dto.ChatDTO;
import com.ohboon.ohboon.dto.MsgDTO;
import org.eclipse.tags.shaded.org.apache.xalan.xsltc.compiler.util.MultiHashtable;

import java.time.LocalDateTime;
import java.util.*;

public class ChatService {
    ChatDAO chatDAO;

    public int saveMsg(MsgDTO msgDTO) {
        MsgDAO msgDAO = new MsgDAO();
        return msgDAO.saveMsg(msgDTO);
    }
//    public List<MsgDTO> getMsgList(long chat_id) {
//        MsgDAO msgDAO = new MsgDAO();
//        return msgDAO.getMsgList(chat_id);
//    }

    public long getChatId(ChatDTO chatRoomDto) {
        ChatDAO chatDAO1 = new ChatDAO();
        int cnt = chatDAO1.countChatRoom(chatRoomDto);

        if(cnt == 0) {
            ChatDAO chatDAO = new ChatDAO();
            return chatDAO.CreateChatRoom(chatRoomDto);
        }
        else return 0;
    }

    public List<String> findUsersByChatId(long chatId) {
        chatDAO = new ChatDAO();
        List<HashMap<String, String>> results = chatDAO.findUsersByChatId(chatId);
        List<String> users = new ArrayList<>();

        for (HashMap<String, String> result : results) {
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

    public Map<LocalDateTime, Map<String, Object>> getMsgMap(long chatId) {
        MsgDAO msgDAO = new MsgDAO();
        List<MsgDTO> list = msgDAO.getMsgList(chatId);

        Map<LocalDateTime, Map<String, Object>> MsgMap = new LinkedHashMap<>();

        for (MsgDTO msgDTO : list) {
            Map<String, Object> msg = new HashMap<>();
            msg.put("sender", msgDTO.getSender());
            msg.put("content", msgDTO.getContent());
            MsgMap.put(msgDTO.getTimeStamp(), msg);
        }
        return MsgMap;
    }

    public Map<Long, Map<String, Object>> getChatList(String userID) {
        ChatDAO chatRoomDAO = new ChatDAO();
        UserDao userDAO;
        MsgDAO msgDAO;
        List<ChatDTO> chatList = chatRoomDAO.getChatList(userID);
        System.out.println("chatList: "+chatList);

        Map<Long, Map<String, Object>> roomMap = new LinkedHashMap<>();
        for (ChatDTO chatDTO : chatList) {
            userDAO = new UserDao();
            msgDAO = new MsgDAO();
            Map<String, Object> map = new HashMap<>();
//            map.put("match_id", chatDTO.getMatchID());
//            map.put("board_id", chatDTO.getBoardID());
//            map.put("sender", chatDTO.getSender());
//            map.put("receiver", chatDTO.getReceiver());
            if (chatDTO.getSender().equals(userID)) {
                map.put("subject", chatDTO.getReceiver());
//                map.put("profile", userDAO.getProfile(chatDTO.getReceiver()));
            } else {
                map.put("subject", chatDTO.getSender());
//                map.put("profile", userDAO.getProfile(chatDTO.getSender()));
            }
            map.put("profile", null);
            map.put("recentContent", msgDAO.getRecentMsg(chatDTO.getChatID()));
            roomMap.put(chatDTO.getChatID(), map);
        }
        System.out.println("roomMap: "+roomMap);
        return roomMap;
    }
}
