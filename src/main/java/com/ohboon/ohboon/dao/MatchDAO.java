package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.MatchDTO;
import com.ohboon.ohboon.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;


import java.util.HashMap;
import java.util.Map;

public class MatchDAO {
    private SqlSession ss = MybatisConnectionFactory.getSqlSession();

    public int createMatch(Map<String, Object> map) {
        System.out.println(map);
        int rs = ss.insert("createMatch", map);
        ss.commit();
        ss.close();
        return rs;
    }

    public long getMatchId(Map<String, Object> map) {
        long match_id = ss.selectOne("getMatchId", map);
        ss.commit();
        ss.close();
        return match_id;
    }


    public String getMatchEmail(long matchId) {
        String match_email = ss.selectOne("getMatchEmail", matchId);
        ss.commit();
        ss.close();
        return match_email;
    }
}
