package com.jhta2402.jhta2402_3_team_project_1.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieManager {

    // static으로 설정하여 따로 생성할 필요가 없게 함
    public static void createEmailCookie(HttpServletResponse response,
                                    String cookieName,
                                    String cookieValue,
                                    int second) {
        // response 값, 쿠키이름, on/off 값, 지속시간,  을 받음
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(second);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String readCookie(HttpServletRequest request, String cookieName) {
        Cookie cookies[] = request.getCookies(); // 리턴타입이 배열임
        String cookieValue = "";
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if(cookie.getName().equals(cookieName)) {
                    cookieValue = cookie.getValue();
                    break; // 원하는 쿠키를 찾으면 반복문 종료
                }
            }
        }
        return cookieValue;
    }

    public static void deleteEmailCookie(HttpServletResponse response, String cookieName) {
        // createEmailCookie()에서 존속시간에 0을 넣음
        createEmailCookie(response, cookieName, null, 0);
    }

}
