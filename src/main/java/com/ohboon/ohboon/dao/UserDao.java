package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.Grade;
import com.ohboon.ohboon.dto.UserDto;
import com.ohboon.ohboon.mail.NaverMail;
import com.ohboon.ohboon.mybatis.MybatisConnectionFactory;
import com.ohboon.ohboon.utils.ScriptWriter;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.ohboon.ohboon.mybatis.MybatisConnectionFactory.sqlSessionFactory;
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
                .userName(req.getParameter("userName"))
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
                .privateField(false)
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

    public UserDto findUserByEmail(String email) {
        UserDto userDto = null;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            userDto = sqlSession.selectOne("findUserByEmail", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDto;
    }


    public boolean sendPasswordByEmail(String email, String link ) {
        Map<String, String> sendMailInfo = new HashMap<>();
        sendMailInfo.put("from", "mgrtest@naver.com");
        sendMailInfo.put("to", email);
        sendMailInfo.put("subject", "비밀번호 재설정 링크");
        sendMailInfo.put("content", "PW 재설정 링크: "+ link);
        sendMailInfo.put("format", "text/plain; charset=utf-8");
        try {
            NaverMail naverMail = new NaverMail();
            naverMail.sendMail(sendMailInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int updatePassword(Map map) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {

            result = sqlSession.update("updatePassword", map);
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

    public boolean savePasswordResetToken(String email, String token) {
        int result;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("email", email);
            params.put("token", token);
            result = sqlSession.update("savePasswordResetToken", params);
            if(result>0) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isTokenValid(String token, String email) {
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            String emailFound = sqlSession.selectOne("isTokenValid", token);
            if(email.equals("")) {
                return false;
            }
            else if (email.equals(emailFound)) return true;
            else return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int updatePasswordByToken(String token, String hashedPassword) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("token", token);
            params.put("hashedPassword", hashedPassword);
            result = sqlSession.update("updatePasswordByToken", params);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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

    public boolean verifyPasswordResetToken(String email, String token) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("token", token);
            Integer count = session.selectOne("UserMapper.verifyPasswordResetToken", params);
            return count != null && count > 0;
        }
    }

    public String getEmailByToken(String token) {
        return null;
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

    public int updateUserInfo(UserDto userDto) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.update("updateUserInfo", userDto);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    // ADMIN 사용자의 열람 가능한 사용자 목록
    public List<UserDto> getAllUsersExcludingAdmin(int offset, int limit) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("offset", offset);
            params.put("limit", limit);
            return session.selectList("getAllUsersExcludingAdmin", params);
        }
    }

    public List<UserDto> getUsersForManager(int offset, int limit) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("offset", offset);
            params.put("limit", limit);
            return session.selectList("getUsersForManager", params);
        }
    }

    // 사용자 숫자 세기
    public int getTotalUserCount(Grade grade) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            if (grade == Grade.ADMIN) {
                return session.selectOne("getTotalUserCountExcludingAdmin");
            } else if (grade == Grade.MANAGER) {
                return session.selectOne("getTotalUserCountForManager");
            }
            return 0;
        }
    }

}
