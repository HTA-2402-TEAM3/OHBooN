package com.jhta2402.jhta2402_3_team_project_1.controller.user;

import com.jhta2402.jhta2402_3_team_project_1.dao.UserDao;
import com.jhta2402.jhta2402_3_team_project_1.dto.Grade;
import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
import com.jhta2402.jhta2402_3_team_project_1.utils.ScriptWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;

@MultipartConfig
@WebServlet("/user/signup")
public class Signup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/user/signup.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //비밀번호 암호화
        String userPW = req.getParameter("userPW");
        String salt = BCrypt.gensalt();
        String hashUserPW = BCrypt.hashpw(userPW, salt);

        // 프로필 이미지 받아서 서버에 저장하기
        Part profile = req.getPart("profile"); // part로 파일 이름 받기. String으로 받으면 안 됨
        String renameProfile = ""; // 서버에 저장하는 파일 이름

        String fileName = profile.getSubmittedFileName();
        String serverUploadDir = this.getServletContext().getRealPath("upload");
        File dir = new File(serverUploadDir); // 파일 저장 위치
        if(!dir.exists()) {
            dir.mkdir(); // 디렉토리 만들기
        }
        if(!fileName.isEmpty()) {
            profile.write(serverUploadDir+File.separator+fileName); //원본파일을 미리 써놓기
            String first = fileName.substring(0, fileName.lastIndexOf(".")); // 파일명 (jun)
            String extention = fileName.substring(fileName.lastIndexOf(".")); // 확장자명 (.jpg)
            LocalDateTime now = now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYYMMdd_hhmmss");
            String formatNow = now.format(dateTimeFormatter); // 20240531_123741
            renameProfile = first + "_" + formatNow + extention; // 재명명된 파일이름(jun_20240531_123741.jpg)

            File oldFile = new File(serverUploadDir + File.separator + fileName);
            File newFile = new File(serverUploadDir + File.separator + renameProfile); // new file은 이름 바꿀려고 만든 것

            Thumbnails.of(oldFile)
                    //.sourceRegion(Positions.CENTER, 100,200) // 원하는 위치로 사각형 잡아서 이미지 잘라내기
                    .size(100, 200) // 가로세로 비율 유지.
                    .toFiles(dir, Rename.NO_CHANGE); // 새로 저장한 파일을 찾아서 사이즈를 줄임(파일 이름은 바꾸지 않음)

            oldFile.renameTo(newFile); // new file 에 덮어쓰기
        }


        // 사용자가 입력한 정보를 이용하여 memberDto 구성
        UserDto userDto = UserDto.builder()
                .email(req.getParameter("email"))
                .nickname(req.getParameter("nickname"))
                .username(req.getParameter("userName"))
                .birth(req.getParameter("birth"))
                .phone(req.getParameter("phone"))
                .available(true)
                .userPW(hashUserPW)
                .grade(Grade.STANDBY)
                .evaluation(0)
                .profile(renameProfile)
                .createDate(now())
                .agreeInfoOffer(Boolean.parseBoolean((req.getParameter("agreeInfoOffer"))))
                .requesttimefordeletion(null)
                .build();

        UserDao userDao = new UserDao();
        //System.out.println(userDto.toString());
        int result = userDao.signup(userDto);
        if(result > 0){
            ScriptWriter.alertAndNext(resp,"계정이 생성되었습니다. 이메일 인증 후에 로그인을 해주세요.", "../index/index");
        }else {
            ScriptWriter.alertAndBack(resp, "알 수 없는 오류 발생. 다시 시도해주세요.");
        }

    }
}
