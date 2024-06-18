package com.ohboon.ohboon.filter.board;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/board/write")
public class WriteFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
		IOException,
		ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
		HttpSession session = httpServletRequest.getSession();

		Object loginEmail = session.getAttribute("loginEmail");
		String boardID = httpServletRequest.getParameter("boardID");
		String subject = httpServletRequest.getParameter("subject");
		String content = httpServletRequest.getParameter("content");
		String meetDate = httpServletRequest.getParameter("meetDate");

		if (Objects.isNull(loginEmail)) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "로그인 후 이용가능한 기능입니다.");
			return;
		}

		if (Objects.nonNull(boardID) && !boardID.matches("^[\\d]+$")) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "게시글 id가 숫자형이 아닙니다.");
			return;
		}

		if (Objects.isNull(subject) || subject.isBlank()) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "제목을 입력해주세요.");
			return;

		} else if(subject.length() > 100) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "제목은 100자를 넘길 수 없습니다..");
			return;
		}

		if (Objects.isNull(content) || content.isBlank()) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "내용을 입력해주세요.");
			return;

		} else if(content.length() > 500) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "내용은 500자를 넘길 수 없습니다.");
			return;
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}
}
