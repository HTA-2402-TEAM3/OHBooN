package com.ohboon.ohboon.controller.board;

import java.io.IOException;

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

		BoardService boardService = new BoardService();
		boardService.delete(boardID);

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.sendRedirect("/board/list");

	}
}
