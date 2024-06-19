//package com.jhta2402.jhta2402_3_team_project_1.controller.user;
//
//import com.jhta2402.jhta2402_3_team_project_1.dao.UserDao;
//import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
//import com.jhta2402.jhta2402_3_team_project_1.utils.ScriptWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.mindrot.jbcrypt.BCrypt;
//
//import java.io.IOException;
//
//@WebServlet("/user/password-check")
//public class PasswordCheck extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/WEB-INF/user/password-check.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        String sessionEmail = (String) session.getAttribute("sessionEmail");
//        String inputPassword = req.getParameter("password");
//
//        if (sessionEmail == null) {
//            ScriptWriter.alertAndBack(resp, "로그인이 필요합니다.");
//            return;
//        }
//
//        UserDao userDao = new UserDao();
//        UserDto userDto = userDao.findUserByEmail(sessionEmail);
//
//        if (userDto == null) {
//            ScriptWriter.alertAndBack(resp, "사용자 정보를 찾을 수 없습니다.");
//            return;
//        }
//
//        String hashedPassword = userDto.getUserPW();
//        if (BCrypt.checkpw(inputPassword, hashedPassword)) {
//            // 비밀번호가 일치하면 정보 수정 페이지로 이동
//            resp.sendRedirect(req.getContextPath() + "/user/info-update");
//        } else {
//            ScriptWriter.alertAndBack(resp, "비밀번호가 일치하지 않습니다.");
//        }
//    }
//}
//
