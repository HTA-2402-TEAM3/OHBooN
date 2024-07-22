package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.MatchDTO;
import com.ohboon.ohboon.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;


import java.util.HashMap;
import java.util.List;
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

    public int isMatched(long matchId) {
        int rs = ss.update("isMatched", matchId);
        ss.commit();
        ss.close();
        return rs;
    }

    public long getBoardID(long matchId) {
        long board_id = ss.selectOne("getBoardID", matchId);
        ss.commit();
        ss.close();
        return board_id;
    }

    public List<MatchDTO> getMatchedMap(String email) {
        List<MatchDTO> rsMap = ss.selectList("getMatchedList", email);
        System.out.println("rsMap : "+rsMap);
        ss.commit();
        ss.close();
        return rsMap;
    }

    public void setEvaluation(String matchID) {
        ss.update("setMatchEvaluation", matchID);
        ss.commit();
        ss.close();
    }
}
