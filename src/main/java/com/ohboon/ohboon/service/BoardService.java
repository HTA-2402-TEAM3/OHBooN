package com.ohboon.ohboon.service;

import java.util.List;
import java.util.Map;
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

	public List<BoardDTO> read(int startIndex, int endIndex) {
		BoardDAO boardDAO = new BoardDAO();
		List<BoardDTO> boardDTOS = boardDAO.findInRange(Map.of("startIndex", startIndex, "endIndex", endIndex));
		boardDAO.close();
		return boardDTOS;
	}

	public void modify(BoardDTO boardDTO) {
		BoardDAO boardDAO = new BoardDAO();
		boardDAO.findByID(boardDTO.getId());

		int modifyResult = boardDAO.modify(boardDTO);

		if (modifyResult <= 0) {
			boardDAO.rollback();
			boardDAO.close();
			throw new IllegalStateException("해당 게시글의 수정에 실패했습니다.");
		}

		boardDAO.commit();
		boardDAO.close();
	}

	public int calculateTotalCount() {
		BoardDAO boardDAO = new BoardDAO();
		int totalCount = boardDAO.calculateTotalCount();
		boardDAO.close();
		return totalCount;
	}

	public void delete(long boardID) {
		BoardDAO boardDAO = new BoardDAO(false);
		int deleteResult = boardDAO.delete(boardID);

		if(deleteResult <= 0) {
			boardDAO.rollback();
		}

		if(deleteResult > 0) {
			boardDAO.commit();
		}

		boardDAO.close();
	}

}
