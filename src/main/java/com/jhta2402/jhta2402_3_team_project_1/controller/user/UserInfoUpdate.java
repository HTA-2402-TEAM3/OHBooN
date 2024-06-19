package com.jhta2402.jhta2402_3_team_project_1.controller.user;

import com.jhta2402.jhta2402_3_team_project_1.dao.UserDao;
import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
import com.jhta2402.jhta2402_3_team_project_1.utils.ScriptWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/user/info/update")
public class UserInfoUpdate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/user/info-update.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("sessionEmail");

        if (email == null) {
            ScriptWriter.alertAndBack(resp, "로그인이 필요합니다.");
            return;
        }

        // 사용자 입력 정보 받아오기
        String phone = req.getParameter("phone");
        boolean agreeInfoOffer = req.getParameter("agreeInfoOffer") != null;
        Part profilePart = req.getPart("profile");
        String profile = uploadProfileImage(profilePart, req);

        UserDao userDao = new UserDao();
        UserDto userDto = userDao.findUserByEmail(email);

        if (userDto != null) {
            userDto.setPhone(phone);
            userDto.setAgreeInfoOffer(agreeInfoOffer);
            if (profile != null) {
                userDto.setProfile(profile);
            }

            int result = userDao.updateUserInfo(userDto);
            if (result > 0) {
                ScriptWriter.alertAndNext(resp, "정보가 성공적으로 변경되었습니다.", "../user/info");
            } else {
                ScriptWriter.alertAndBack(resp, "정보 변경에 실패했습니다.");
            }
        } else {
            ScriptWriter.alertAndBack(resp, "사용자 정보를 찾을 수 없습니다.");
        }
    }

    private String uploadProfileImage(Part profilePart, HttpServletRequest req) throws IOException {
        if (profilePart == null || profilePart.getSize() == 0) {
            return null;
        }

        String fileName = profilePart.getSubmittedFileName();
        String serverUploadDir = req.getServletContext().getRealPath("upload");
        File dir = new File(serverUploadDir);

        if (!dir.exists()) {
            dir.mkdir();
        }

        String ext = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ext;
        String filePath = serverUploadDir + File.separator + newFileName;

        profilePart.write(filePath);

        return newFileName;
    }
}
