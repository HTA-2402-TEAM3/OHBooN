package com.jhta2402.jhta2402_3_team_project_1.dao;

import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
import com.jhta2402.jhta2402_3_team_project_1.mail.NaverMail;
import com.jhta2402.jhta2402_3_team_project_1.mybatis.MybatisConnectionFactory;
import com.jhta2402.jhta2402_3_team_project_1.mybatis.UserMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

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

//            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//            infoUserDto = userMapper.findUserByNickname(nickname);

            //디버깅 로그
            System.out.println("User found: " + (infoUserDto != null ? infoUserDto.toString() : "null"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infoUserDto;
    }

    public UserDto adminUser(String grade) {
        UserDto adminUserDto = null;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            adminUserDto = sqlSession.selectOne("adminUser", grade);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminUserDto;
    }

    public boolean sendPasswordByEmail(String email, String link ) {


        Map<String, String> sendMailInfo = new HashMap<>();
        sendMailInfo.put("from", "mgrtest@naver.com");
        sendMailInfo.put("to", email);
        sendMailInfo.put("subject", "비밀번호 재설정 링크");
        sendMailInfo.put("content", "PW 재설정 링크: "+ link);
        sendMailInfo.put("format", "text/plain; charset=utf-8");
        try{
            NaverMail naverMail = new NaverMail();
            naverMail.sendMail(sendMailInfo);
            System.out.println("success to send e-mail");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("fail to send e-mail");
        }

        return true;
    }

}
