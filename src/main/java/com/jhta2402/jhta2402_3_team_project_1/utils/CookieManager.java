package com.jhta2402.jhta2402_3_team_project_1.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieManager {

    // static으로 설정하여 따로 생성할 필요가 없게 함
    public static void createCookie(HttpServletResponse response,
                                    String cookieName,
                                    String cookieValue,
                                    int second) {
        // response 값, 쿠키이름, on/off 값, 지속시간,  을 받음
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(second);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    //cookie 읽는 method 만들기 -> return 값은 String
    // 쿠키는 따로 지우는 명령어가 없음.
    // 동일한 이름으로 쿠키를 생성하면서 지속시간을 0으로 설정하면 쿠키를 삭제할 수 있음.

    //readCookie, deleteCookie 만들기

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

    public static void deleteCookie(HttpServletResponse response, String cookieName) {
        // createCookie()에서 존속시간에 0을 넣음
        createCookie(response, cookieName, null, 0);
    }

}
