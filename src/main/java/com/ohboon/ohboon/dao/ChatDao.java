package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.ChatDTO;
import org.apache.ibatis.session.SqlSession;
import util.MybatisConnectionFactory;

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
}
