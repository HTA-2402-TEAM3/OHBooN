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

		httpServletResponse.setContentType("application/json");
		httpServletResponse.setCharacterEncoding("UTF-8");

		if (Objects.isNull(loginEmail)) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			httpServletResponse.getWriter().write("{\"message\": \"로그인 후 이용가능한 기능입니다.\"}");

			return;
		}

		if (Objects.nonNull(boardID) && !boardID.matches("^[\\d]+$")) {
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			httpServletResponse.getWriter().write("{\"message\": \"게시글 id가 숫자형이 아닙니다.\"}");
			return;
		}

		if (Objects.nonNull(subject)) {

			if (subject.isBlank()) {

				httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				httpServletResponse.getWriter().write("{\"message\": \"제목을 입력해주세요.\"}");
				return;

			} else if (subject.length() > 100) {

				httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				httpServletResponse.getWriter().write("{\"message\": \"제목은 100자를 넘길 수 없습니다.\"}");
				return;
			}
		}

		if (Objects.nonNull(content)) {

			if (content.isBlank()) {

				httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				httpServletResponse.getWriter().write("{\"message\": \"내용을 입력해주세요.\"}");
				return;

			} else if (content.length() > 500) {

				httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				httpServletResponse.getWriter().write("{\"message\": \"내용은 500자를 넘길 수 없습니다.\"}");
				return;
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}
}
