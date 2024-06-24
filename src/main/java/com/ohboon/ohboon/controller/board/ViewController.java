package com.ohboon.ohboon.controller.board;

import java.io.IOException;

import com.ohboon.ohboon.dto.BoardDTO;
import com.ohboon.ohboon.service.BoardService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/board/view")
public class ViewController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		long boardID = Long.parseLong(req.getParameter("boardID"));

		BoardService boardService = new BoardService();

		try {
			BoardDTO board = boardService.search(boardID);
			req.setAttribute("board", board);
			req.getRequestDispatcher("/WEB-INF/board/view.jsp").forward(req, resp);

		} catch (IllegalStateException illegalStateException) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, illegalStateException.getMessage());
		}

	}
}
