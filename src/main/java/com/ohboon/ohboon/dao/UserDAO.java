package com.ohboon.ohboon.dao;

import com.ohboon.ohboon.dto.Grade;
import com.ohboon.ohboon.dto.UserDTO;
import com.ohboon.ohboon.mail.NaverMail;
import com.ohboon.ohboon.mybatis.MybatisConnectionFactory;
import com.ohboon.ohboon.utils.ScriptWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.ohboon.ohboon.mybatis.MybatisConnectionFactory.sqlSessionFactory;
import static java.time.LocalDateTime.now;

public class UserDao {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String resource = "/config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String findNicknameByEmail(String boardWriterName) {
        String nickname = null;
        try (
            SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            nickname = sqlSession.selectOne("findNicknameByEmail", boardWriterName);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nickname;
    }

    public Object getProfile(String name) {
        String nickname = null;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            nickname = sqlSession.selectOne("findProfileByName", name);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nickname;
    }

    public void setEvaluation(Map<String, String> map) {
        try(SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            sqlSession.update("setEvaluation", map);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    // UserDTO 생성
    public UserDTO createUserDTO(HttpServletRequest req, String hashUserPW, String renameProfile, String verificationCode) {
        return UserDTO.builder()
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
    public int registerUser(UserDTO userDto) {
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
    public int saveUser(UserDTO userDto) {
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

    // DB에 이메일 존재 여부 검사
    public int emailCheck(String email) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.selectOne("emailCheck", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // DB에 닉네임 존재 여부 검사
    public int nicknameCheck(String nickname) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.selectOne("nicknameCheck", nickname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public UserDTO loginUser(UserDTO userDto) {
        UserDTO loginMemberDto = null;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            loginMemberDto = sqlSession.selectOne("loginUser", userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginMemberDto;
    }

    public UserDTO findUserByEmail(String email) {
        UserDTO userDto = null;
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
            UserDTO userDto = new UserDTO();

            userDto.setEmail(email);
            userDto.setNickname(nickname);
            userDto.setPhone(phone);

            result = sqlSession.update("updateUserInfo", userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int updateUserInfo(UserDTO userDto) {
        int result = 0;
        try (SqlSession sqlSession = MybatisConnectionFactory.getSqlSession()) {
            result = sqlSession.update("updateUserInfo", userDto);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    // ADMIN 관리자의 열람 가능한 사용자 목록 - 검색어 없음
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

    // MANAGER 관리자의 열람 가능한 사용자 목록 - 검색어 없음
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

    // 관리가자 접근 가능한 사용자 숫자 세기 - 검색어 있음(없을 경우도 사용 가능)
    public int getTotalUserCount(Grade grade, String searchField, String searchKeyword) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            if(grade == Grade.ADMIN || grade == Grade.MANAGER){
                Map<String, Object> params = new HashMap<>();
                params.put("grade", grade);
                params.put("searchField", searchField);
                params.put("searchKeyword", searchKeyword);
                return session.selectOne("getTotalUserCount", params);
            }
            return 0;
        }
    }

    // ADMIN 사용자의 열람 가능한 사용자 목록 - 검색어 있음(없을 경우도 사용 가능)
    public List<UserDto> getAllUsersExcludingAdmin(int offset, int limit, String searchField, String searchKeyword, String sortField, String sortOrder) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("offset", offset);
            params.put("limit", limit);
            params.put("searchField", searchField);
            params.put("searchKeyword", searchKeyword);
            params.put("sortField", sortField);
            params.put("sortOrder", sortOrder);
            return session.selectList("getAllUsersExcludingAdmin", params);
        }
    }

    // ADMIN 사용자의 열람 가능한 사용자 목록 - 검색어 있음(없을 경우도 사용 가능)
    public List<UserDto> getUsersForManager(int offset, int limit, String searchField, String searchKeyword, String sortField, String sortOrder) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("offset", offset);
            params.put("limit", limit);
            params.put("searchField", searchField);
            params.put("searchKeyword", searchKeyword);
            params.put("sortField", sortField);
            params.put("sortOrder", sortOrder);
            return session.selectList("getUsersForManager", params);
        }
    }

    // 프로필 사진 삭제
    public void clearUserProfile(String email) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("clearUserProfile", email);
            session.commit();
        }
    }

    // 닉네임 존재 여부 확인(어드민 페이지 닉네임 변경시 사용)
    public boolean isNicknameExists(String nickname) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int count = session.selectOne("isNicknameExists", nickname);
            return count > 0;
        }
    }

    // 닉네임 업데이트(어드민 페이지 닉네임 변경시 사용)
    public int updateNickname(String email, String newNickname) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("newNickname", newNickname);
            int result = session.update("updateNickname", params);
            session.commit();
            return result;
        }
    }
}