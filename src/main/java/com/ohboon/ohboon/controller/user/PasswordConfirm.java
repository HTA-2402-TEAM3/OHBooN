package com.ohboon.ohboon.controller.user;

import com.ohboon.ohboon.dao.UserDAO;
import com.ohboon.ohboon.dto.UserDTO;
import com.ohboon.ohboon.utils.ScriptWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
//사용자 정보 수정 페이지에서 PW 변경시 사용
@WebServlet("/user/info/password-confirm")
public class PasswordConfirm extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/user/password-confirm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");

        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("sessionEmail");

        UserDAO userDao = new UserDAO();
        UserDTO userDto = userDao.findUserByEmail(email);

        if (userDto != null && BCrypt.checkpw(password, userDto.getUserPW())) {
            session.setAttribute("passwordVerified", true);
            resp.sendRedirect(req.getContextPath() + "/user/info/update"); // 정보 수정 페이지로 리디렉션
        } else {
            ScriptWriter.alertAndBack(resp, "비밀번호가 일치하지 않습니다.");
        }
    }
}
