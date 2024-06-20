package com.ohboon.ohboon.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.ohboon.ohboon.dto.BoardDTO;
import com.ohboon.ohboon.dto.ListBoardDTO;
import com.ohboon.ohboon.util.MybatisConnectionFactory;

public class BoardDAO {

	private final SqlSession sqlSession;

	public BoardDAO() {
		this.sqlSession = MybatisConnectionFactory.getSqlSession();
	}

	public BoardDAO(boolean isCommit) {
		this.sqlSession = MybatisConnectionFactory.getSqlSession(isCommit);
	}

	public Optional<BoardDTO> findByID(long id) {
		return Optional.ofNullable(sqlSession.selectOne("findByID", id));
	}

	public int save(BoardDTO boardDTO) {
		return this.sqlSession.insert("save", boardDTO);
	}

	public int modify(BoardDTO boardDTO) {
		return this.sqlSession.update("modify", boardDTO);
	}

	public List<ListBoardDTO> findWithSimple(Map<String, String> searchOptions) {
		return this.sqlSession.selectList("findWithSimple", searchOptions);
	}

	public List<ListBoardDTO> findBySearchWord(Map<String, String> searchOptions) {
		return this.sqlSession.selectList("findBySearchWord", searchOptions);
	}

	public int calculateWordSearchBoardCount(Map<String, String> searchOptions) {
		return this.sqlSession.selectOne("calculateWordSearchBoardCount", searchOptions);
	}

	public int calculateSimpleSearchBoardCount(Map<String, String> searchOptions) {
		return this.sqlSession.selectOne("calculateSimpleSearchBoardCount", searchOptions);
	}

	public int delete(long boardID) {
		return this.sqlSession.update("delete", boardID);
	}

	public void close() {
		sqlSession.close();
	}

	public void rollback() {
		sqlSession.rollback();
	}

	public void commit() {
		sqlSession.commit();
	}

}
