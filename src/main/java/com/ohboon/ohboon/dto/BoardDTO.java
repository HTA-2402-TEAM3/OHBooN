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
public class BoardDTO {
	private long id;
	private String email;
	private String subject;
	private String content;
	private LocalDateTime regDate;
	private int viewOption;
	private String category;
	private String location;
	private LocalDateTime meetDate;
	private LocalDateTime modifyDate;
}
