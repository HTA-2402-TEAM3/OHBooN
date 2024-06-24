package com.ohboon.ohboon.controller.user;


import com.ohboon.ohboon.dao.UserDAO;
import com.ohboon.ohboon.dto.UserDto;
import com.ohboon.ohboon.utils.ScriptWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/user/info/update")
@MultipartConfig //multipart로 데이터 보낼 때 반드시 필요

public class UserInfoUpdate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("sessionEmail");

        UserDAO userDao = new UserDAO();
        UserDto infoUserDto = userDao.findUserByEmail(email);

        if (infoUserDto == null) {
            ScriptWriter.alert(resp,"사용자 메일 정보를 찾을 수 없습니다.");
            resp.sendRedirect("/index/index");
        } else {
            req.setAttribute("infoUserDto", infoUserDto);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user/info-update.jsp");
            dispatcher.forward(req, resp);
        }
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


        if (phone == null || phone.trim().isEmpty()) {
            ScriptWriter.alertAndBack(resp, "전화번호를 입력해주세요.");
            return;
        }

        UserDAO userDao = new UserDAO();
        UserDto userDto = userDao.findUserByEmail(email);

        if (userDto != null) {
            userDto.setPhone(phone);
            userDto.setAgreeInfoOffer(agreeInfoOffer);
            if (profile != null) {
                userDto.setProfile(profile);
            }

            int result = userDao.updateUserInfo(userDto);
            if (result > 0) {
                // 세션 정보 갱신
                session.setAttribute("sessionPhone", userDto.getPhone());
                session.setAttribute("sessionAgreeInfoOffer", userDto.isAgreeInfoOffer());
                if (profile != null) {
                    session.setAttribute("sessionProfile", userDto.getProfile());
                }

                ScriptWriter.alertAndNext(resp, "정보가 성공적으로 변경되었습니다.", "/user/info");
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
