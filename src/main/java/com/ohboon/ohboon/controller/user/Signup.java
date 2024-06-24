package com.ohboon.ohboon.controller.user;


import com.ohboon.ohboon.dao.UserDao;
import com.ohboon.ohboon.dto.UserDto;
import com.ohboon.ohboon.utils.ScriptWriter;
import com.ohboon.ohboon.utils.VerificationCodeGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;


@MultipartConfig
@WebServlet("/user/signup")
public class Signup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/user/signup.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDao userDao = new UserDao();

        // 비밀번호 암호화
        String userPW = req.getParameter("userPW");
        String hashUserPW = userDao.hashPassword(userPW);

        // 프로필 이미지 저장
        Part profile = req.getPart("profile");
        String serverUploadDir = this.getServletContext().getRealPath("upload");
        String renameProfile = userDao.saveProfileImage(profile, serverUploadDir);

        // 이메일 인증코드 생성
        String verificationCode = VerificationCodeGenerator.generateRandomCode();

        // UserDto 생성
        UserDto userDto = userDao.createUserDto(req, hashUserPW, renameProfile, verificationCode);

        // 사용자 등록 및 이메일 전송
        int result = userDao.registerUser(userDto);
        if (result > 0) {
            ScriptWriter.alertAndNext(resp, "계정이 생성되었습니다. 이메일로 코드를 발송하였으니 인증 후에 로그인을 해주세요.", "../index/index");
        } else {
            ScriptWriter.alertAndBack(resp, "알 수 없는 오류 발생. 다시 시도해주세요.");
        }

    }
}
