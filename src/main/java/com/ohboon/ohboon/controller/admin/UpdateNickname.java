package com.ohboon.ohboon.controller.admin;

import com.ohboon.ohboon.dao.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/updateNickname")
public class UpdateNickname extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String nickname = req.getParameter("nickname");

        req.setAttribute("email", email);
        req.setAttribute("nickname", nickname);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/admin/updateNickname.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String newNickname = req.getParameter("newNickname");

        UserDAO userDao = new UserDAO();

        boolean isNicknameExists = userDao.isNicknameExists(newNickname);
        if (isNicknameExists) {
            resp.getWriter().write("Fail: Nickname duplicated");
            return;
        }

        int result = userDao.updateNickname(email, newNickname);
        if (result > 0) {
            resp.getWriter().write("success to change");
        } else {
            resp.getWriter().write("[error] fail to change");
        }
    }
}
