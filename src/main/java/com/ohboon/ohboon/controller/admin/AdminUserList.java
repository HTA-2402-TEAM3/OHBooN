package com.ohboon.ohboon.controller.admin;

import com.ohboon.ohboon.dao.UserDAO;
import com.ohboon.ohboon.dto.Grade;
import com.ohboon.ohboon.dto.UserDto;
import com.ohboon.ohboon.utils.ScriptWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/userList")
public class AdminUserList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Grade grade = (Grade) session.getAttribute("sessionGrade");
        UserDAO userDao = new UserDAO();
        List<UserDto> userList;

        // 사용자 목록 페이징을 위한 인덱스 변수 설정
        String pageStr = req.getParameter("page");
        String limitStr = req.getParameter("limit");
        int page = pageStr != null ? Integer.parseInt(pageStr) : 1;
        int limit = (limitStr != null && !limitStr.equals("all")) ? Integer.parseInt(limitStr) : 10;
        int offset = (page - 1) * limit;
        int totalCount = userDao.getTotalUserCount(grade);
        int totalPages = (int) Math.ceil((double) totalCount / limit);

        // 사용지 등급 확인
        if (grade == Grade.ADMIN) {
            userList = userDao.getAllUsersExcludingAdmin(offset, limit);
        } else if (grade == Grade.MANAGER) {
            userList = userDao.getUsersForManager(offset, limit);
        } else {
            ScriptWriter.alertAndBack(resp, "열람 권한이 없습니다.");
            return;
        }

        // 목록 페이징을 위해서 필요한 변수값을 JSP에서 받기
        req.setAttribute("userList", userList);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("limit", limit);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/admin/admin-userList.jsp");
        dispatcher.forward(req, resp);
    }
}
