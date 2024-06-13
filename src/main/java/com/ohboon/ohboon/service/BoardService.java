package com.ohboon.ohboon.service;

import org.apache.ibatis.session.SqlSession;
import util.MybatisConnectionFactory;

public class BoardService {
    public String findEmailByBoardId(long id){
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        String userEmail = sqlSession.selectOne("findEmailByBoardId", id);
        sqlSession.commit();
        sqlSession.close();
        return userEmail;
    }
}
