package com.jhta2402.jhta2402_3_team_project_1.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;


@WebFilter("/index/index") // 모든 페이지에 다 적용하겠다는 뜻
public class Encoding implements Filter {

    private String encoding = "UTF-8";

    public Encoding() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("filtering start!!");
        if(request.getCharacterEncoding()==null){
            request.setCharacterEncoding(encoding);
            System.out.println("utf-8 encoding");
        }
        chain.doFilter(request, response);
    }

}
