package com.ohboon.ohboon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
@ToString
public class MsgDTO {
    private long messageId;
    private long chatRoomId;
    private long matchId;
    private String sender;
    private String content;
    private LocalDateTime timeStamp;
}