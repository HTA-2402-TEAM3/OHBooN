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

        req.getRequestDispatcher("/WEB-INF/member/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = UserDto
                .builder()
                .email(req.getParameter("email"))
                .build();
        UserDao userDao = new UserDao();
        UserDto loginMemberDto = userDao.loginUser(userDto);

        // email과 password 받아서 이것을 암호화하여 PW 확인



        if (loginMemberDto != null) {
            String encodedPassword = loginMemberDto.getUserPW(); // 암호화된 비밀번호
            if(BCrypt.checkpw(req.getParameter("userPW"), encodedPassword)){ // BCrypt.checkpw( A, B ): 비밀번호 A와, B의 일치여부 확인

                //여기가 진짜 로그인 -> 세션에서 아이디, 이름, 등급, 프로필사진 받음
                HttpSession session = req.getSession();
                session.setAttribute("sessionEmail", loginMemberDto.getEmail());
                session.setAttribute("sessionNickname", loginMemberDto.getNickname());
                session.setAttribute("sessionGrade", loginMemberDto.getGrade());
                session.setAttribute("profile", loginMemberDto.getProfile());

                ModalDto modalDto = new ModalDto("로그인", "로그인되었습니다.","show");

                /*
                // 방법1 -> 리퀘스트, 리스폰스에 싣기
                req.setAttribute("modal", modalDto);
                String sendMsg = URLEncoder.encode("?title=로그인되었습니다.&msg=로그인되었습니다.&isState=show");
                resp.sendRedirect("../index/index"+sendMsg);*/


                // 방법2 -> session에 싣기 (모달 이용)
                HttpSession session02 = (HttpSession)req.getSession();
                session02.setAttribute("modal", modalDto);
                resp.sendRedirect("../index/index");


                //ScriptWriter.alertAndNext(resp, "로그인되었습니다.", "../index/index");
            }
        }else{
            ScriptWriter.alertAndBack(resp, "unknown error occurred");
        }
    }
}


