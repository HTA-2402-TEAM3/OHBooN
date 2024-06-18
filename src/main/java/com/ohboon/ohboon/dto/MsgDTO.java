package com.ohboon.ohboon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MsgDTO {
    private int messageId;
    private int boardId;
    private int matchId;
    private String userName;
    private String content;
    private LocalDateTime timeStamp;
}