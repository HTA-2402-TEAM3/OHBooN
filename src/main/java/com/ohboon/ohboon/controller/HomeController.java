package com.ohboon.ohboon.controller;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;

import com.ohboon.ohboon.dao.BoardDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.MybatisConnectionFactory;

@WebServlet("/") //jsp랑 연결
public class HomeController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/index/index.jsp")
			.forward(req,resp);
	}

	// @Override
	// protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// 	super.doPost(req, resp);
	// }
}