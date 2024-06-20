package com.ohboon.ohboon.controller.board;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ohboon.ohboon.dto.ListBoardDTO;
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
		String searchOption = req.getParameter("searchOption");
		String searchWord = req.getParameter("searchWord");
		String strMeetDate = req.getParameter("meetDate");
		String category = req.getParameter("category");

		int page = 0;
		if (strPage != null && !strPage.isBlank()) {
			page = Integer.parseInt(strPage);
		}

		BoardService boardService = new BoardService();

		int startPage = (page / 10) * 10;
		int endPage = startPage + 9;

		int startIndex = page * 10 + 1;
		int endIndex = startIndex + 9;

		HashMap<String, String> searchOptions = new HashMap<>(Map.of(
			"searchOption", "",
			"searchWord", "",
			"startIndex", String.valueOf(startIndex),
			"endIndex", String.valueOf(endIndex)));

		int totalCount = 0;
		List<ListBoardDTO> boards = new ArrayList<>();

		if (Objects.isNull(strMeetDate)) {

			searchOptions.put("searchOption", searchOption);
			searchOptions.put("searchWord", searchWord);

			boards = boardService.search(searchOptions);
			totalCount = boardService.calculateWordSearchBoardCount(searchOptions);

			req.setAttribute("searchOption", searchOption);
			req.setAttribute("searchWord", searchWord);
		}

		if (Objects.nonNull(strMeetDate)) {

			String firstSelect = req.getParameter("firstSelect");
			String secondSelect = req.getParameter("secondSelect");
			String location = firstSelect + " " + secondSelect;

			strMeetDate = strMeetDate.replace("T", " ");

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime searchMeetDate = LocalDateTime.parse(strMeetDate, formatter);
			LocalDateTime minTime = LocalDateTime.parse(strMeetDate, formatter).minusHours(2);
			LocalDateTime maxTime = LocalDateTime.parse(strMeetDate, formatter).plusHours(2);

			searchOptions.put("location", location);
			searchOptions.put("minTime", minTime.format(formatter));
			searchOptions.put("maxTime", maxTime.format(formatter));
			searchOptions.put("category", category);

			boards = boardService.search(searchOptions, searchMeetDate);
			totalCount = boardService.calculateSimpleSearchBoardCount(searchOptions);

			req.setAttribute("firstSelect", firstSelect);
			req.setAttribute("secondSelect", secondSelect);
			req.setAttribute("category", category);
			req.setAttribute("meetDate", searchMeetDate);
		}

		int totalPage = totalCount / 10;

		if (totalCount > 10 && totalCount % 10 == 0) {
			totalPage--;
		}

		if (endPage >= totalPage) {
			endPage = totalPage;
		}

		req.setAttribute("boards", boards);
		req.setAttribute("startPage", String.valueOf(startPage));
		req.setAttribute("endPage", String.valueOf(endPage));
		req.setAttribute("totalPage", String.valueOf(totalPage));
		req.setAttribute("totalCount", String.valueOf(totalCount));

		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/board/list.jsp");
		requestDispatcher.forward(req, resp);
	}
}
