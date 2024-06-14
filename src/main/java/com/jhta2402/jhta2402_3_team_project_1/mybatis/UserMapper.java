package com.jhta2402.jhta2402_3_team_project_1.mybatis;

import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserMapper {
    int signup(UserDto userDto);

    int emailCheck(@Param("email") String email);

    int nicknameCheck(@Param("nickname") String nickname);

    UserDto loginUser(String email, String userPW);

    UserDto infoUser(@Param("nickname") String nickname);
}
