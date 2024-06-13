package com.ohboon.ohboon.dao;

import org.apache.ibatis.session.SqlSession;
import util.MybatisConnectionFactory;

public class BoardDAO {
    public String findEmailByBoardId(long id){
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        String userEmail = sqlSession.selectOne("findEmailByBoardId", id);
        sqlSession.commit();
        sqlSession.close();
        return userEmail;
    }
}
