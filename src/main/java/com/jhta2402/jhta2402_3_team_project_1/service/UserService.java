package com.jhta2402.jhta2402_3_team_project_1.service;

import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
import com.jhta2402.jhta2402_3_team_project_1.mail.NaverMail;
import com.jhta2402.jhta2402_3_team_project_1.mybatis.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.Map;

import static com.jhta2402.jhta2402_3_team_project_1.mybatis.MybatisConnectionFactory.sqlSessionFactory;

public class UserService {

    private UserMapper userMapper;

//    public boolean isEmailExists(String email) {
//        return userMapper.emailCheck(email) > 0;
//    }
//    public boolean isNicknameExists(String nickname) {
//        return userMapper.nicknameCheck(nickname) > 0;
//    }
//
//    public void signup(UserDto user) {
//        userMapper.signup(user);
//    }
//
//    public UserDto loginUser(String email, String userPW) {
//        return userMapper.loginUser(email, userPW);
//    }
//
//    public UserDto infoUser(String nickname) {
//        return userMapper.infoUser(nickname);
//    }

}
