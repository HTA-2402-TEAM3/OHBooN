package com.ohboon.ohboon.controller.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

		String inputBoardID = req.getParameter("boardID");

		BoardService boardService = new BoardService();

		if (Objects.nonNull(inputBoardID)) {
			long boardID = Long.parseLong(inputBoardID);

			try {
				BoardDTO boardDTO = boardService.search(boardID);
				req.setAttribute("board", boardDTO);

			} catch (IllegalStateException illegalStateException) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, illegalStateException.getMessage());
				return;
			}
		}

		req.getRequestDispatcher("/WEB-INF/board/write.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String userEmail = (String)session.getAttribute("loginEmail");

		String id = req.getParameter("boardID");

		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String category = req.getParameter("category");
		String location = req.getParameter("location");
		String inputMeetDate = req.getParameter("meetDate");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime meetDate = LocalDateTime.parse(inputMeetDate, formatter);

		BoardDTO boardDTO = BoardDTO.builder()
			.email(userEmail)
			.subject(subject)
			.content(content)
			.viewOption(1)
			.category(category)
			.location(location)
			.meetDate(meetDate)
			.build();

		BoardService boardService = new BoardService();

		try {

			String writeMessage;

			if (Objects.nonNull(id) && !id.isBlank()) {
				boardDTO.setId(Long.parseLong(id));
				boardService.modify(boardDTO);
				writeMessage = "게시글 수정에 성공했습니다.";

			} else {
				boardService.write(boardDTO);
				writeMessage = "게시글 작성에 성공했습니다.";
			}

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.setStatus(HttpServletResponse.SC_OK);
			PrintWriter writer = resp.getWriter();
			writer.println("{\"writeMessage\": \"" + writeMessage + "\"}");
			writer.flush();

		} catch (IllegalStateException illegalStateException) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, illegalStateException.getMessage());
		}

	}
}
