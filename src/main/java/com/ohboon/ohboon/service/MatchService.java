package com.ohboon.ohboon.service;

import com.ohboon.ohboon.dao.MatchDAO;

public class MatchService {
    public long getMatchId(long board_id, String boardWriterName) {
        MatchDAO matchDAO = new MatchDAO();
        return matchDAO.createMatch(board_id, boardWriterName);
    }

}
