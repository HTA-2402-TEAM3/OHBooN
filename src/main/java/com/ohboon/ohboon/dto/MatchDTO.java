package com.ohboon.ohboon.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MatchDTO {
    private long matchID;
    private long boardIDX;
    private boolean isMatch;
    private String email;
    private String sender;
}
