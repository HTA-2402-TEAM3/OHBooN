package com.jhta2402.jhta2402_3_team_project_1.dao;

import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
import com.jhta2402.jhta2402_3_team_project_1.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class UserDao {
    // 2tier, 3tier 방식
    // html(front) -> controller(프론트에서 넘어온 데이터 처리) --> service dao 매서드 호출
    // -> dao에서 db에 crud(mybatis) 하고 결과값 들고 오기

    public int insertMember(UserDto userDto) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.insert("insertMember", userDto);
        sqlSession.close();
        return result;
    }

    public int emailCheck(String email) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.insert("emailCheck", email);
        sqlSession.close();
        return result;
    }

    public int nicknameCheck(String nickname) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.insert("nicknameCheck", nickname);
        sqlSession.close();
        return result;
    }

    public UserDto loginUser(UserDto userDto) {
        UserDto loginMemberDto = null;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        loginMemberDto = sqlSession.selectOne("loginUser", userDto);
        sqlSession.close();
        return loginMemberDto;
    }

    public UserDto infoUser(String email) {
        UserDto infoMemberDto = null;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        infoMemberDto = sqlSession.selectOne("loginUser", email);
        sqlSession.close();
        return infoMemberDto;
    }
}
