package com.jhta2402.jhta2402_3_team_project_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter, toString 포함
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String email;
    private String nickname;
    private String username;
    private String birth;
    private String phone;
    private boolean available;
    private String userPW;
    private Grade grade;
    private String meetingLocation;
    private String meetingTime;
    private int evaluation;
    private String profile;
}