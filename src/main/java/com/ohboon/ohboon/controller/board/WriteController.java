package com.ohboon.ohboon.controller.board;

import java.io.IOException;
import java.util.Optional;

import com.ohboon.ohboon.dto.BoardDTO;
import com.ohboon.ohboon.service.BoardService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/board/write")
public class WriteController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		session.setAttribute("loginEmail", "aa@aa.com");

		Optional<String> inputBoardID = Optional.ofNullable(req.getParameter("boardID"));

		BoardService boardService = new BoardService();

		if (inputBoardID.isPresent()) {
			long boardID = Long.parseLong(inputBoardID.get());

			try {
				BoardDTO boardDTO = boardService.read(boardID);
				req.setAttribute("board", boardDTO);
				System.out.println(boardDTO);

			} catch (IllegalStateException illegalStateException) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, illegalStateException.getMessage());
			}
		}

		req.getRequestDispatcher("/WEB-INF/board/write.jsp").forward(req, resp);
	}
}
