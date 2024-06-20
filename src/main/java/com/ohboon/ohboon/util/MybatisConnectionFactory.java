package com.ohboon.ohboon.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MybatisConnectionFactory {
    private static SqlSessionFactory sqlSessionFactory;

    static {

        String resource = "config.xml";

        try {
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);
    }

    public static SqlSession getSqlSession(boolean isCommit) {
        return sqlSessionFactory.openSession(isCommit);
    }

}
