package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.ChatDTO;
import org.apache.ibatis.session.SqlSession;
import util.MybatisConnectionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatDAO {
    private SqlSession ss = MybatisConnectionFactory.getSqlSession();

    public long CreateChatRoom(ChatDTO ChatDTO) {
        long chat_id = 0;
        int result = 0;
        result = ss.insert("createChatRoom", ChatDTO);
        ss.commit();
        if (result > 0) {
            chat_id = ss.selectOne("getChatID", ChatDTO);
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
        long matchId = ss.selectOne("getMatchIdByChatId", chatId);
        ss.commit();
        ss.close();
        return matchId;
    }
}
