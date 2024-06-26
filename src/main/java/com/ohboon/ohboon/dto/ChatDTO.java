package com.ohboon.ohboon.dto;

import lombok.*;
import org.apache.ibatis.session.ResultHandler;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatDTO {
    private long chatID;
    private long matchID;
    private long boardID;
    private String sender;
    private String receiver;
}
