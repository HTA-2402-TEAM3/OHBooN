package com.ohboon.ohboon.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatDTO {
    private int chatID;
    private int matchID;
    private int boardID;
    private String sender;
    private String receiver;
}
//채팅방 DTO