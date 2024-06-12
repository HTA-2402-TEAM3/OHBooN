package com.ohboon.ohboon.service;

import java.util.Optional;

import com.ohboon.ohboon.dao.BoardDAO;
import com.ohboon.ohboon.dto.BoardDTO;

public class BoardService {

	public void write(BoardDTO boardDTO) {
		BoardDAO boardDAO = new BoardDAO(false);
		int saveResult = boardDAO.save(boardDTO);

		if (saveResult <= 0) {
			boardDAO.rollback();
			boardDAO.close();
			throw new IllegalArgumentException("게시글 작성에 실패하였습니다. 다시 시도해주세요");
		}

		boardDAO.commit();
		boardDAO.close();
	}

	public BoardDTO read(long id) {

		BoardDAO boardDAO = new BoardDAO();
		Optional<BoardDTO> byID = boardDAO.findByID(id);
		boardDAO.close();

		return byID.orElseThrow(() -> new IllegalStateException("해당하는 게시글이 없습니다."));
	}

}
