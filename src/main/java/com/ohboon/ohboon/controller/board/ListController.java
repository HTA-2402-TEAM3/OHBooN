package com.ohboon.ohboon.controller.board;

import java.io.IOException;
import java.util.List;

import com.ohboon.ohboon.dto.BoardDTO;
import com.ohboon.ohboon.service.BoardService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/board/list")
public class ListController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String strPage = req.getParameter("page");

		int page = 0;
		if (strPage != null && !strPage.isBlank()) {
			page = Integer.parseInt(strPage);
		}
		BoardService boardService = new BoardService();
		int totalCount = boardService.calculateTotalCount();

		int totalPage = totalCount / 10;

		if(totalCount > 10 && totalCount % 10 != 0) {
			totalPage++;
		}

		int startPage = (page / 10) * 10;
		int endPage = startPage + 9;
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		int startIndex = page * 10 + 1;
		int endIndex = startIndex + 9;
		List<BoardDTO> boards = boardService.read(startIndex, endIndex);

		System.out.println(boards);

		req.setAttribute("boards", boards);
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("totalCount", totalCount);
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/board/list.jsp");
		requestDispatcher.forward(req, resp);
	}
}
