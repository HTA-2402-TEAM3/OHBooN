package com.ohboon.ohboon.controller.user;


import com.ohboon.ohboon.dao.UserDAO;
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
import java.util.regex.Pattern;


@MultipartConfig
@WebServlet("/user/signup")
public class Signup extends HttpServlet {

    // 사용자 입력정보의 유효성을 서버에서 검증하기 위한 변수
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "^[0-9]{9,14}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{9,20}$";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/user/signup.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String userPW = req.getParameter("userPW");
        String username = req.getParameter("username");
        String birth = req.getParameter("birth");
        String phone = req.getParameter("phone");

        // 입력정보 검증
        if (!isValidEmail(email)) {
            ScriptWriter.alertAndBack(resp, "유효한 이메일 주소를 입력해주세요.");
            return;
        }

        if (!isValidPassword(userPW)) {
            ScriptWriter.alertAndBack(resp, "비밀번호는 영문 대소문자 각 1개 이상, 숫자 1개 이상, 특수문자 1개 이상 포함하여 9~20자리여야 합니다.");
            return;
        }

        if (!isValidPhone(phone)) {
            ScriptWriter.alertAndBack(resp, "유효한 전화번호를 입력해주세요.");
            return;
        }

        if (username == null || username.trim().isEmpty()) {
            ScriptWriter.alertAndBack(resp, "이름을 입력해주세요.");
            return;
        }

        if (birth == null || birth.trim().isEmpty()) {
            ScriptWriter.alertAndBack(resp, "생년월일을 입력해주세요.");
            return;
        }

        UserDAO userDao = new UserDAO();

        
        // 비밀번호 암호화
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

    private boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

    private boolean isValidPassword(String password) {
        return password != null && Pattern.matches(PASSWORD_REGEX, password);
    }

    private boolean isValidPhone(String phone) {
        return phone != null && Pattern.matches(PHONE_REGEX, phone);
    }

}
