package com.jhta2402.jhta2402_3_team_project_1.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String email;
    private String nickname;
    private String userName;
    private String birth;
    private String phone;
    private boolean available;
    private String userPW;
    private Grade grade;
    private int evaluation;
    private String profile;
    private String createDate;
}