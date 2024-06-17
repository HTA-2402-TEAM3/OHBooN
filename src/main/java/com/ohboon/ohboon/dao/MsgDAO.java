package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.MsgDTO;
import org.apache.ibatis.session.SqlSession;
import util.MybatisConnectionFactory;

import java.util.List;

public class MsgDAO {
    public List<MsgDTO> getMsgList(int msgChatID) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        List<MsgDTO> msgList = sqlSession.selectList
                ("getMsgList",msgChatID);
        sqlSession.commit();
        sqlSession.close();
        return msgList;
    }
}
// chatid를 넘기는 부분을 추가해보자
//selectOne 하나 selectList 여러개 불러오는거