package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.MsgDTO;
import org.apache.ibatis.session.SqlSession;
import util.MybatisConnectionFactory;

public class MsgDAO {
    private SqlSession ss = MybatisConnectionFactory.getSqlSession();
    public int saveMsg(MsgDTO msgDTO) {
        int rs = ss.insert("saveMsg", msgDTO);
        ss.close();
        return rs;
    }
}
