package com.ohboon.ohboon.service;

import com.ohboon.ohboon.dao.ChatDAO;
import com.ohboon.ohboon.dao.MatchDAO;

import java.util.HashMap;
import java.util.Map;

public class MatchService {
    public long getMatchId(long board_id, String boardWriterName, String senderName) {
        Map<String, Object> map = new HashMap<>();
        map.put("board_id", board_id);
        map.put("boardWriterName", boardWriterName);
        map.put("senderName", senderName);

        MatchDAO matchCreateDAO = new MatchDAO();
       matchCreateDAO.createMatch(map);

        long match_id = 0;

        Map<String, Object> map0 = new HashMap<>();
        map0.put("board_id", board_id);
        map0.put("senderName", senderName);

        MatchDAO matchDAO = new MatchDAO();

        match_id = matchDAO.getMatchId(map0);
        return match_id;
    }
}
