package com.jhta2402.jhta2402_3_team_project_1.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisConnectionFactory {
    // 싱글톤 패턴...
    private static SqlSessionFactory sqlSessionFactory;
    static {
        String resource = "config.xml"; // resources 안에 있는 파일은 특별한 경로 없이 접근 가능
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            System.out.println("resource load success");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            System.out.println("session load success");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true); // 자동커밋
        // (false) == 커밋 안 함.
    }

    public static SqlSession getSqlsession(boolean isCommit) {
        return sqlSessionFactory.openSession(isCommit); //
    }
}
