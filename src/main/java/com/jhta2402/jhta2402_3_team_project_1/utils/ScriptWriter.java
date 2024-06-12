package com.jhta2402.jhta2402_3_team_project_1.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ScriptWriter {
    public static void alert(HttpServletResponse response, String msg) { // 일반 경고창
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert(\""+msg+"\");</script>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void alertAndNext(HttpServletResponse response, String msg, String url) { // 경고창 띄우고 다른 페이지로 보내기
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert(\""+msg+"\"); location.href=\""+url+"\";</script>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void alertAndBack(HttpServletResponse response, String msg) { // 경고창 띄우고 이전 페이지로 보내기
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert(\""+msg+"\"); history.back();</script>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
