package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.ChatDTO;
import com.ohboon.ohboon.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatDAO {
    private SqlSession ss = MybatisConnectionFactory.getSqlSession();

    public long CreateChatRoom(ChatDTO chatDTO) {
        long chat_id = 0;
        int result = 0;
        result = ss.insert("createChatRoom", chatDTO);
        ss.commit();
        if (result > 0) {
            chat_id = ss.selectOne("getChatID", chatDTO);
        }
        ss.close();
        return chat_id;
    }

    public List<HashMap<String,String>> findUsersByChatId(long chatId) {
        List<HashMap<String, String>> rs = ss.selectList("findUsersByChatId", chatId);
        ss.commit();
        ss.close();
        return rs;
    }

    public long getMatchIdByChatId(long chatId) {
        long matchId = 0;
        long match = ss.selectOne("getCountMatchByChatId", chatId);
        if(match!=0) {
            try {
                ss.selectOne("getMatchIdByChatId", chatId);
            } catch (NullPointerException e) {
                throw new NullPointerException();
            }
        }
        ss.commit();
        ss.close();
        return matchId;
    }

    public List<ChatDTO> getChatList(String userId) {
        List<ChatDTO> chatList;
//        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        chatList = ss.selectList("getChatList", userId);
        ss.commit();
        ss.close();
        return chatList;
    }

    public int insertMatchId(Map<String, Long> map) {
        int rs = ss.update("insertMatchId", map);
        ss.commit();
        ss.close();
        return rs;
    }

    public int countChatRoom(ChatDTO chatRoomDto) {
        int rs = ss.selectOne("countChatRoom", chatRoomDto);
        ss.commit();
        ss.close();
        return rs;
    }
}
