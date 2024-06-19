package com.jhta2402.jhta2402_3_team_project_1.dao;

import com.jhta2402.jhta2402_3_team_project_1.dto.Grade;
import com.jhta2402.jhta2402_3_team_project_1.dto.UserDto;
import com.jhta2402.jhta2402_3_team_project_1.mail.NaverMail;
import com.jhta2402.jhta2402_3_team_project_1.mybatis.MybatisConnectionFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.apache.ibatis.session.SqlSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.time.LocalDateTime.now;

public class UserDao {

    // 비밀번호 해시화
    public String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    // 프로필 이미지 저장 및 리네임
    public String saveProfileImage(Part profile, String serverUploadDir) throws IOException {
        String renameProfile = "";
        String fileName = profile.getSubmittedFileName();
        File dir = new File(serverUploadDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (!fileName.isEmpty()) {
            profile.write(serverUploadDir + File.separator + fileName);
            String first = fileName.substring(0, fileName.lastIndexOf("."));
            String extention = fileName.substring(fileName.lastIndexOf("."));
            LocalDateTime now = now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");
            String formatNow = now.format(dateTimeFormatter);
            renameProfile = first + "_" + formatNow + extention;

            File oldFile = new File(serverUploadDir + File.separator + fileName);
            File newFile = new File(serverUploadDir + File.separator + renameProfile);

            Thumbnails.of(oldFile)
                    .size(100, 200)
                    .toFiles(dir, Rename.NO_CHANGE);

            oldFile.renameTo(newFile);
        }
        return renameProfile;
    }

    // UserDto 생성
    public UserDto createUserDto(HttpServletRequest req, String hashUserPW, String renameProfile, String verificationCode) {
        return UserDto.builder()
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
                .agreeInfoOffer(Boolean.parseBoolean(req.getParameter("agreeInfoOffer")))
                .requestTimeForDeletion(null)
                .verificationCode(verificationCode)
                .build();
    }

    // 사용자 저장 및 이메일 전송
    public int registerUser(UserDto userDto) {
        int result = saveUser(userDto);
        if (result > 0) {
            String verificationCode = UUID.randomUUID().toString();
            saveVerificationToken(userDto.getEmail(), verificationCode);
            boolean emailSent = sendVerificationEmail(userDto.getEmail(), verificationCode);
            if (!emailSent) { // 이메일 전송 실패 시 사용자 삭제 처리
                deleteUser(userDto.getEmail());
                result = 0;
            }
        }
        return result;
    }

    // 사용자 저장
    public int saveUser(UserDto userDto) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.insert("signup", userDto);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 사용자 삭제
    public int deleteUser(String email) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.delete("deleteUser", email);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 이메일 전송
    public boolean sendVerificationEmail(String email, String verificationCode) {
        String verificationLink = "http://localhost:8080/user/verify-email?email=" + email + "&token=" + verificationCode;

        Map<String, String> sendMailInfo = new HashMap<>();
        sendMailInfo.put("from", "mgrtest@naver.com");
        sendMailInfo.put("to", email);
        sendMailInfo.put("subject", "이메일 인증 코드");
        sendMailInfo.put("content", "인증 링크를 클릭하여 이메일을 인증하세요: <a href=\"" + verificationLink + "\">여기를 클릭</a>");
        sendMailInfo.put("format", "text/html; charset=utf-8");

        try {
            NaverMail naverMail = new NaverMail();
            naverMail.sendMail(sendMailInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 이메일 인증 토큰 저장
    public int saveVerificationToken(String email, String token) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("email", email);
            params.put("token", token);
            result = sqlSession.update("saveVerificationToken", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int emailCheck(String email) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.selectOne("emailCheck", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int nicknameCheck(String nickname) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.selectOne("nicknameCheck", nickname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public UserDto loginUser(UserDto userDto) {
        UserDto loginMemberDto = null;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            loginMemberDto = sqlSession.selectOne("loginUser", userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginMemberDto;
    }

    public UserDto infoUser(String nickname) {
        UserDto infoUserDto = null;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            infoUserDto = sqlSession.selectOne("infoUser", nickname);

            //디버깅 로그
            System.out.println("User found: " + (infoUserDto != null ? infoUserDto.toString() : "null"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infoUserDto;
    }


    public boolean sendPasswordByEmail(String email, String link ) {
        Map<String, String> sendMailInfo = new HashMap<>();
        sendMailInfo.put("from", "mgrtest@naver.com");
        sendMailInfo.put("to", email);
        sendMailInfo.put("subject", "비밀번호 재설정 링크");
        sendMailInfo.put("content", "PW 재설정 링크: "+ link);
        sendMailInfo.put("format", "text/plain; charset=utf-8");
        try{
            NaverMail naverMail = new NaverMail();
            naverMail.sendMail(sendMailInfo);
            System.out.println("success to send e-mail");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("fail to send e-mail");
        }
        return true;
    }

    public int updatePassword(String email, String newPassword) {
        int result = 0;
        String [] newPW = {email, newPassword};
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.update("updatePassword", newPW);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int alterStandbyToMember(String email, String verificationCode) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.update("AlterStandbyToMember", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void savePasswordResetToken(String email, String token) {
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("email", email);
            params.put("token", token);
            sqlSession.update("com.jhta2402.mybatis.UserMapper.savePasswordResetToken", params);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTokenValid(String token) {
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            int count = sqlSession.selectOne("com.jhta2402.mybatis.UserMapper.isTokenValid", token);
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updatePasswordByToken(String token, String newPassword) {
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("token", token);
            params.put("password", BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            sqlSession.update("com.jhta2402.mybatis.UserMapper.updatePasswordByToken", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 이메일 인증
    public int verifyEmail(String email, String token) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("email", email);
            params.put("token", token);
            result = sqlSession.update("verifyEmail", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String gradeCheck(String email) {
        String grade = "";
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            grade = (String) sqlSession.selectOne("gradeCheck", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grade;
    }

    public UserDto findUserByEmail(String email) {
        UserDto user = null;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            user = sqlSession.selectOne("findUserByEmail", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public int updateUserInfo(String email, String nickname, String phone) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            UserDto userDto = new UserDto();

            userDto.setEmail(email);
            userDto.setNickname(nickname);
            userDto.setPhone(phone);

            result = sqlSession.update("updateUserInfo", userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
