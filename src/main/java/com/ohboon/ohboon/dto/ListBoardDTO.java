package com.ohboon.ohboon.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ListBoardDTO {
	private long id;
	private String nickName;
	private int evaluation;
	private LocalDateTime regDate;
	private String category;
	private String subject;
	private LocalDateTime meetDate;
	private String location;
	private int viewOption;
}
