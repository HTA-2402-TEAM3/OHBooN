package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.MatchDTO;
import org.apache.ibatis.session.SqlSession;
import util.MybatisConnectionFactory;

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
}
