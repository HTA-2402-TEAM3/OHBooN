package com.ohboon.ohboon.controller.board;

import java.io.IOException;
import java.io.PrintWriter;

import com.ohboon.ohboon.service.BoardService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/board/delete")
public class DeleteController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		long boardID = Long.parseLong(req.getParameter("boardID"));

		try {

			BoardService boardService = new BoardService();
			boardService.delete(boardID);

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter writer = resp.getWriter();
			resp.setStatus(HttpServletResponse.SC_OK);
			writer.println("{\"deleteMessage\":\"게시글이 삭제되었습니다.\"}");

		} catch (IllegalStateException illegalStateException) {

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter writer = resp.getWriter();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			writer.println("{\"deleteMessage\":\"" + illegalStateException.getMessage() + "\"}");
		}
	}
}
