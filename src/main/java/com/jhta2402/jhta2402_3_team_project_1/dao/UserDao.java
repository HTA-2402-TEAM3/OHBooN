package com.jhta2402.jhta2402_3_team_project_1.dao;

import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
import com.jhta2402.jhta2402_3_team_project_1.mybatis.MybatisConnectionFactory;
import com.jhta2402.jhta2402_3_team_project_1.mybatis.UserMapper;
import org.apache.ibatis.session.SqlSession;

import static com.jhta2402.jhta2402_3_team_project_1.mybatis.MybatisConnectionFactory.sqlSessionFactory;

public class UserDao {


    public int signup(UserDto userDto) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.insert("signup", userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int emailCheck(String email) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.selectOne("emailCheck", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int nicknameCheck(String nickname) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.selectOne("nicknameCheck", nickname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public UserDto loginUser(UserDto userDto) {
        UserDto loginMemberDto = null;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            loginMemberDto = sqlSession.selectOne("loginUser", userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginMemberDto;
    }

    public UserDto infoUser(String nickname) {
        UserDto infoUserDto = null;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            infoUserDto = sqlSession.selectOne("infoUser", nickname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infoUserDto;
    }
}
