package com.ohboon.ohboon.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDTO {
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
    private LocalDateTime createDate;
    private boolean agreeInfoOffer;
    private LocalDateTime requestTimeForDeletion;
    private String verificationCode;
    private boolean privateField;

    public String getFormattedCreateDate() {
        return createDate != null ? createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
    }
}