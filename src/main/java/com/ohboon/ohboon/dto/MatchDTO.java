package com.ohboon.ohboon.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchDTO {
    private long id;
    private long boardId;
    private boolean isMath;
//    private String boardWriter;
}
