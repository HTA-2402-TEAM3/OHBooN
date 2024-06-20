package com.ohboon.ohboon.controller.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.ohboon.ohboon.dto.BoardDTO;
import com.ohboon.ohboon.service.BoardService;
import com.ohboon.ohboon.util.CommonValidation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@MultipartConfig
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

		System.out.println("subject = " + subject);
		System.out.println("content = " + content);
		System.out.println("inputMeetDate = " + inputMeetDate);
		System.out.println("location = " + location);
		System.out.println("category = " + category);

		try {
			CommonValidation.validateNull(subject, content, category, location, inputMeetDate);
			CommonValidation.validateBlank(subject, content, inputMeetDate);
		} catch (IllegalStateException illegalStateException) {
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			PrintWriter writer = resp.getWriter();
			writer.write("{\"message\":\"" + illegalStateException.getMessage() + "\"}");
			writer.flush();
			return;
		}

		inputMeetDate = inputMeetDate.replace("T", " ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
			PrintWriter writer = resp.getWriter();
			resp.setStatus(HttpServletResponse.SC_OK);
			writer.println("{\"writeMessage\":\"" + writeMessage + "\"}");
			writer.flush();

		} catch (IllegalStateException e) {

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			PrintWriter writer = resp.getWriter();
			writer.write("{\"message\":\"" + e.getMessage() + "\"}");
			writer.flush();
		}

	}
}
