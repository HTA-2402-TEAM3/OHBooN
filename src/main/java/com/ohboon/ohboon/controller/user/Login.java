package com.ohboon.ohboon.controller.user;

import com.ohboon.ohboon.dao.UserDAO;
import com.ohboon.ohboon.dto.Grade;
import com.ohboon.ohboon.dto.ModalDto;
import com.ohboon.ohboon.dto.UserDTO;
import com.ohboon.ohboon.utils.CookieManager;
import com.ohboon.ohboon.utils.ScriptWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

import static com.ohboon.ohboon.dto.Grade.*;

//로그인 페이지
@WebServlet("/user/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ModalDto modalDto = new ModalDto("", "", "");
        req.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String userPW = req.getParameter("userPW");

        UserDTO userDto = UserDTO
                .builder()
                .email(req.getParameter("email"))
                .build();
        UserDAO userDao = new UserDAO();
        UserDTO loginUserDTO = userDao.loginUser(userDto);

        if (loginUserDTO == null) {
            // 아이디가 없는 경우
            System.out.println("email doesnot exist");
            ScriptWriter.alertAndBack(resp, "이메일 또는 패스워드가 일치하지 않습니다.");
        }
        // email과 password 받아서 이것을 암호화하여 PW 확인
        else {
            String encodedPassword = loginUserDTO.getUserPW(); // 암호화된 비밀번호
            if (BCrypt.checkpw(req.getParameter("userPW"), encodedPassword)) { // BCrypt.checkpw( A, B ): 비밀번호 A와, B의 일치여부 확인

                //로그인 -> 세션에서 아이디, 이름, 등급, 프로필사진 정보를 담음
                HttpSession session = req.getSession();
                session.setAttribute("sessionEmail", loginUserDTO.getEmail());
                session.setAttribute("sessionNickname", loginUserDTO.getNickname());
                session.setAttribute("sessionGrade", loginUserDTO.getGrade());
                session.setAttribute("sessionProfile", loginUserDTO.getProfile());
                session.setAttribute("sessionUserName", loginUserDTO.getUserName());
                session.setAttribute("sessionBirth", loginUserDTO.getBirth());
                session.setAttribute("sessionPhone", loginUserDTO.getPhone());
                session.setAttribute("sessionEvaluation", loginUserDTO.getEvaluation());
                session.setAttribute("sessionCreateDate", loginUserDTO.getCreateDate());
                session.setAttribute("sessionAgreeInfoOffer", loginUserDTO.isAgreeInfoOffer());

                String nickname = loginUserDTO.getNickname();
                Grade grade = loginUserDTO.getGrade();

                ModalDto modalDto;

                if (grade == STANDBY) {
                    modalDto = new ModalDto("이메일 인증 안내", nickname + "님 환영합니다. 이메일 인증 후에 다시 로그인해주세요.", "show");
                    session.invalidate();
                } else if (grade == MEMBER) {
                    modalDto = new ModalDto("로그인되었습니다.", nickname + "님 환영합니다~♥", "show");
                } else if (grade == MANAGER) {
                    modalDto = new ModalDto("관리자 계정", nickname + "님 - 관리자 계정 로그인", "show");
                } else if (grade == ADMIN) {
                    modalDto = new ModalDto("시스템 관리자 계정", nickname + "님 - 시스템 관리자 계정 로그인", "show");
                } else if (grade == RESTRICTED1) {
                    modalDto = new ModalDto("서비스 이용 제한 안내", nickname + "님 께서는 규정 위반으로 1개월 간 게시판 글 작성이 제한됩니다.", "show");
                    // (참고) user 테이블에 계정 정지 시작 및 종료시간 표기를 위한 column 필요
                } else if (grade == RESTRICTED2) {
                    modalDto = new ModalDto("서비스 이용 제한 안내", nickname + "님 께서는 규정 위반으로 3개월 간 게시판 접속이 제한됩니다.", "show");
                } else if (grade == BANNED) {
                    modalDto = new ModalDto("계정 이용 정지 제한 안내", nickname + "님 께서는 규정 위반으로 계정 이용이 정지되셨습니다. 로그인이 불가합니다.", "show");
                    session.invalidate();
                } else {
                    modalDto = new ModalDto("오류", "알 수 없는 오류", "show");
                    session.invalidate();
                }

                String saveID = req.getParameter("saveID");
                if (saveID != null && saveID.equals("yes")) {
                    // 이메일 정보를 쿠키에 저장
                    CookieManager.createEmailCookie(resp, "emailCookie", email, 60 * 60 * 24 * 30); // 30일 유효
                } else {
                    CookieManager.deleteEmailCookie(resp, "emailCookie");
                }

                HttpSession session02 = req.getSession(); // 모달을 session02에 싣기
                session02.setAttribute("modal", modalDto);
                resp.sendRedirect("../index/index");
            } else {
                // 비밀번호가 일치하지 않는 경우
                System.out.println("password is not matching");
                ScriptWriter.alertAndBack(resp, "이메일 또는 패스워드가 일치하지 않습니다.");
            }
        }
    }
}


