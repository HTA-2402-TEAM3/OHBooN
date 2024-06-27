package com.ohboon.ohboon.controller.admin;

import com.ohboon.ohboon.dao.UserDAO;
import com.ohboon.ohboon.dto.Grade;
import com.ohboon.ohboon.dto.UserDTO;
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
        List<UserDTO> userList = null;

        // 검색 및 정렬을 위한 변수 설정
        String searchKeyword = req.getParameter("searchKeyword");
        String searchField = req.getParameter("searchField");
        String sortField = req.getParameter("sortField");
        String sortOrder = req.getParameter("sortOrder");

        // 검색어가 null일 경우 빈 문자열로 설정
        if (searchKeyword == null) {searchKeyword = "";}

        // 사용자 목록 페이징을 위한 인덱스 변수 설정
        String pageStr = req.getParameter("page");
        String limitStr = req.getParameter("limit");
        if (limitStr == null || limitStr.isEmpty() ) {limitStr = "10"; } // 기본값: 페이지당 10명 출력으로 설정

        // page 는 현재 페이지
        int page = 1;
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageStr.trim());
            } catch (NumberFormatException e) {
                page = 1; // 기본값으로 설정
            }
        }

        if (page < 1) {page = 1;} // page 에 1보다 작은 수가 입력될 경우 기본값(1)으로 설정

        // limit 은 페이지당 출력 항목수
        int limit = 10;
        if (!limitStr.equals("all")) {
            try {
                limit = Integer.parseInt(limitStr.trim());
            } catch (NumberFormatException e) {
                limit = 10; // 기본값으로 설정
            }
        } else {
            limit = Integer.MAX_VALUE;
        }
        if(limit < 1) {limit = 10;} // limit 에 1보다 작은 수가 입력될 경우 기본값(10)으로 설정

        // totalCount는 테이블에 출력되는 사용자의 숫자
        int totalCount = 0;
        if(userDao.getTotalUserCount(grade, searchField, searchKeyword) > 0 ){
            totalCount = userDao.getTotalUserCount(grade, searchField, searchKeyword);
        }

        // totalPages는 총 출력되는 페이지 갯수
        int totalPages = (int) Math.ceil((double) totalCount / limit);
        if (totalPages == 0) {totalPages = 1;} // totalPages 가 0일 경우 예외처리
        if ( page > totalPages) {page = totalPages;} // page 입력값이 최대 페이지를 초과할 경우 page를 최종 페이지로 재설정

        //페이지 그룹 계산
        int pageGroupSize = 10;
        int currentPageGroup = (int) Math.ceil((double) page / pageGroupSize);
        int groupStartPage = (currentPageGroup - 1) * pageGroupSize + 1;
        int groupEndPage = Math.min(groupStartPage + pageGroupSize - 1, totalPages);

        // offset은 쿼리에서 한 번에 가져오는 사용자 List의 페이지 당 시작시점을 결정: (현재 페이지 - 1) * 페이지당 항목 수
        int offset = (page - 1) * limit;

        // 사용자 등급 확인
        /*if (grade == Grade.ADMIN && searchKeyword.isEmpty()) {
            userList = userDao.getAllUsersExcludingAdmin(offset, limit);
        }else
         */
            if(grade == Grade.ADMIN){
            userList = userDao.getAllUsersExcludingAdmin(offset, limit, searchField, searchKeyword, sortField, sortOrder);
        }
            /*
        else if (grade == Grade.MANAGER && searchKeyword.isEmpty()) {
            userList = userDao.getUsersForManager(offset, limit);
        }
        */
        else if (grade == Grade.MANAGER) {
            userList = userDao.getUsersForManager(offset, limit, searchField, searchKeyword, sortField, sortOrder);
        }

        // 검색 및 정렬을 위한 변수값을 JSP에 보내기
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("searchKeyword", searchKeyword);
        req.setAttribute("searchField", searchField);
        req.setAttribute("sortField", sortField);
        req.setAttribute("sortOrder", sortOrder);

        // 목록 페이징을 위해서 필요한 변수값을 JSP에 보내기
        req.setAttribute("userList", userList);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("limit", limit);
        req.setAttribute("pageGroupSize", pageGroupSize);
        req.setAttribute("groupStartPage", groupStartPage);
        req.setAttribute("groupEndPage", groupEndPage);
        req.setAttribute("currentPageGroup", currentPageGroup);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/admin/admin-userList.jsp");
        dispatcher.forward(req, resp);
    }
}
