package com.jhta2402.jhta2402_3_team_project_1.controller.user;

import com.jhta2402.jhta2402_3_team_project_1.dao.UserDao;
import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
import com.jhta2402.jhta2402_3_team_project_1.dto.ModalDto;
import com.jhta2402.jhta2402_3_team_project_1.utils.ScriptWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/user/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ModalDto modalDto = new ModalDto("", "", "");

        /*
        ModalDto modalDto = new ModalDto("로그인", "로그인해주세요.", "show");
        req.setAttribute("modal", modalDto);

         */

        req.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = UserDto
                .builder()
                .email(req.getParameter("email"))
                .build();
        UserDao userDao = new UserDao();
        UserDto loginUserDto = userDao.loginUser(userDto);

        // email과 password 받아서 이것을 암호화하여 PW 확인
        if (loginUserDto != null) {
            String encodedPassword = loginUserDto.getUserPW(); // 암호화된 비밀번호
            if(BCrypt.checkpw(req.getParameter("userPW"), encodedPassword)){ // BCrypt.checkpw( A, B ): 비밀번호 A와, B의 일치여부 확인

                //로그인 -> 세션에서 아이디, 이름, 등급, 프로필사진 정보를 담음
                HttpSession session = req.getSession();
                session.setAttribute("sessionEmail", loginUserDto.getEmail());
                session.setAttribute("sessionNickname", loginUserDto.getNickname());
                session.setAttribute("sessionGrade", loginUserDto.getGrade());
                session.setAttribute("profile", loginUserDto.getProfile());

                String nickname =loginUserDto.getNickname();

                ModalDto modalDto = new ModalDto("로그인되었습니다.", nickname+"님 환영합니다~♥","show");
                HttpSession session02 = req.getSession(); // 모달을 session02에 싣기
                session02.setAttribute("modal", modalDto);
                resp.sendRedirect("../index/index");

            }
        }else{
            ScriptWriter.alertAndBack(resp, "아이디 또는 패스워드가 일치하지 않습니다.");
        }
    }
}


