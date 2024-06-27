package com.ohboon.ohboon.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ohboon.ohboon.dao.BoardDAO;
import com.ohboon.ohboon.dto.BoardDTO;
import com.ohboon.ohboon.dto.ListBoardDTO;
import com.ohboon.ohboon.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

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

	public BoardDTO search(long id) {

		BoardDAO boardDAO = new BoardDAO();
		Optional<BoardDTO> byID = boardDAO.findByID(id);
		boardDAO.close();

		return byID.orElseThrow(() -> new IllegalStateException("해당하는 게시글이 없습니다."));
	}

	public List<ListBoardDTO> search(Map<String, String> searchOptions) {
		BoardDAO boardDAO = new BoardDAO();
		List<ListBoardDTO> boards = boardDAO.findBySearchWord(searchOptions);
		boardDAO.close();
		return boards;
	}

	public List<ListBoardDTO> search(Map<String, String> searchOptions, LocalDateTime searchMeetDate) {
		BoardDAO boardDAO = new BoardDAO();
		List<ListBoardDTO> boards = boardDAO.findWithSimple(searchOptions);
		boardDAO.close();

		boards.sort(Comparator.comparing(
			listBoardDTO -> Math.abs(listBoardDTO.getMeetDate().until(searchMeetDate, ChronoUnit.MINUTES))
		));

		return boards;
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

	public int calculateWordSearchBoardCount(Map<String, String> searchOptions) {
		BoardDAO boardDAO = new BoardDAO();
		int totalCount = boardDAO.calculateWordSearchBoardCount(searchOptions);
		boardDAO.close();
		return totalCount;
	}

	public int calculateSimpleSearchBoardCount(Map<String, String> searchOptions) {
		BoardDAO boardDAO = new BoardDAO();
		int totalCount = boardDAO.calculateSimpleSearchBoardCount(searchOptions);
		boardDAO.close();
		return totalCount;
	}

	public void delete(long boardID) {
		BoardDAO boardDAO = new BoardDAO(false);
		int deleteResult = boardDAO.delete(boardID);

		if (deleteResult <= 0) {
			boardDAO.rollback();
			throw new IllegalStateException("게시글 삭제에 실패했습니다. 다시 시도해주세요");
		}

		if (deleteResult > 0) {
			boardDAO.commit();
		}

		boardDAO.close();
	}
  
//  public String findEmailByBoardId(long id){
//        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
//        String userEmail = sqlSession.selectOne("findEmailByBoardId", id);
//        sqlSession.commit();
//        sqlSession.close();
//        return userEmail;
//    }


}
