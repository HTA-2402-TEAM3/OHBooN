package com.ohboon.ohboon.service;


import com.ohboon.ohboon.dao.BoardDAO;
import com.ohboon.ohboon.dao.MatchDAO;
import com.ohboon.ohboon.dto.MatchDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchService {
    public long getMatchId(long board_id, String boardWriterName, String senderEmail) {
        Map<String, Object> map = new HashMap<>();
        map.put("board_id", board_id);
        map.put("boardWriterName", boardWriterName);
        map.put("senderName", senderEmail);

        MatchDAO matchCreateDAO = new MatchDAO();
       matchCreateDAO.createMatch(map);

        long match_id = 0;

        Map<String, Object> map0 = new HashMap<>();
        map0.put("board_id", board_id);
        map0.put("senderName", senderEmail);

        MatchDAO matchDAO = new MatchDAO();

        match_id = matchDAO.getMatchId(map0);
        return match_id;
    }

    public long isMatched(long matchId) {
        MatchDAO matchDAO = new MatchDAO();
        int rs = matchDAO.isMatched(matchId);
        long board_id = 0;

        if(rs > 0) {
            MatchDAO matchDAO1 = new MatchDAO();
            board_id = matchDAO1.getBoardID(matchId);
        }
        return board_id;
    }

    public List<MatchDTO> matchEvaluation(String email) {
        MatchDAO matchDAO = new MatchDAO();
        List<MatchDTO> matchedMapL = matchDAO.getMatchedMap(email);
        System.out.println("matchedMap: "+matchedMapL);
        return matchedMapL;
    }

    public void matchBoard(long boardID) {
        BoardDAO boardDAO = new BoardDAO(false);

        int match = boardDAO.match(boardID);
        if (match <= 0) {
            boardDAO.rollback();
        } else {
            boardDAO.commit();
        }
        boardDAO.close();
    }
}
