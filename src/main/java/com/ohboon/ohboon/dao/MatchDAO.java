package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.MatchDTO;
import org.apache.ibatis.session.SqlSession;
import util.MybatisConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class MatchDAO {
    private SqlSession ss = MybatisConnectionFactory.getSqlSession();

    public long createMatch(long board_id, String boardWriterName) {
        long match_id = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("board_id", board_id);
        map.put("boardWriterName", boardWriterName);
        System.out.println(map);
        int rs = ss.insert("createMatch", map);
        ss.commit();

        if(rs > 0 ){
            match_id = ss.selectOne("getMatchId", map);
        }
        ss.commit();
        ss.close();
        return match_id;
    }
}
