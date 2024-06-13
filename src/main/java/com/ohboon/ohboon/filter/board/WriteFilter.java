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

		if (Objects.isNull(session.getAttribute("loginEmail"))) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "로그인 후 이용가능한 기능입니다.");
			return;
		}

		String boardID = httpServletRequest.getParameter("boardID");

		if (Objects.nonNull(boardID) && !boardID.matches("^[\\d]+$")) {
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "게시글 id가 숫자형이 아닙니다.");
			return;
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

}
