package com.ohboon.ohboon.dao;

import org.apache.ibatis.session.SqlSession;
import util.MybatisConnectionFactory;

public class UserDAO {
    private SqlSession ss = MybatisConnectionFactory.getSqlSession();
    public String getProfile(String sender) {
        String profile = ss.selectOne("getProfile",sender);
        ss.commit();
        ss.close();
        return profile;
    }
}
