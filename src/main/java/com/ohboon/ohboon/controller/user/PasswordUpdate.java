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
import java.util.HashMap;
import java.util.Map;

// 회원정보 수정 페이지에서 PW 변경시 동작하는 서블릿

@WebServlet("/user/info/password-update")
public class PasswordUpdate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/user/password-update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("sessionEmail");

        if (email == null) {
            ScriptWriter.alertAndBack(resp, "로그인이 필요합니다.");
            return;
        }

        String newPassword = req.getParameter("newPW");
        UserDAO userDao = new UserDAO();
        UserDTO userDto = userDao.findUserByEmail(email);

        if (userDto != null) {
            try{
                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                Map<String, Object> map = new HashMap<>();
                map.put("password",hashedPassword);
                map.put("email",email);
                int result = userDao.updatePassword(map);

                if (result > 0) {
                    ScriptWriter.alertAndNext(resp, "비밀번호가 성공적으로 변경되었습니다.", "/user/info");
                } else {
                    ScriptWriter.alertAndBack(resp, "비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
                }
            }catch(Exception e) {
                e.printStackTrace();
                ScriptWriter.alertAndBack(resp, "비밀번호 암호화에 실패하였습니다. 다시 시도해도 안 될 경우 관리자에게 문의하세요");
            }
        } else {
            ScriptWriter.alertAndBack(resp, "사용자 정보를 찾을 수 없습니다.");
        }
    }
}
