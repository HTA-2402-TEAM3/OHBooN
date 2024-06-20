package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.MsgDTO;
import org.apache.ibatis.session.SqlSession;
import util.MybatisConnectionFactory;

import java.util.List;

public class MsgDAO {
    private SqlSession ss = MybatisConnectionFactory.getSqlSession();
    public int saveMsg(MsgDTO msgDTO) {
        int rs = ss.insert("saveMsg", msgDTO);
        ss.close();
        return rs;
    }

    public List<MsgDTO> getMsgList(long chat_id) {
        List<MsgDTO> msgList = ss.selectList("getMsgList",chat_id);
        ss.commit();
        ss.close();

        System.out.println(msgList);
        return msgList;
    }

    public String getRecentMsg(long chatID) {
        String msg = ss.selectOne("getRecentMsg", chatID);
        ss.commit();
        ss.close();
        return msg;
    }
}
