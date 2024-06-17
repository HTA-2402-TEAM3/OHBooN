package com.ohboon.ohboon.service;

import com.ohboon.ohboon.dao.ChatDAO;
import com.ohboon.ohboon.dao.MatchDAO;

import java.util.HashMap;
import java.util.Map;

public class MatchService {
    public long getMatchId(long board_id, String boardWriterName) {
        Map<String, Object> map = new HashMap<>();
        map.put("board_id", board_id);
        map.put("boardWriterName", boardWriterName);

        long match_id = 0;

        MatchDAO matchDAO = new MatchDAO();
        if(matchDAO.createMatch(map) > 0) {
            match_id = matchDAO.getMatchId(map);
        }
        return match_id;
    }
}
