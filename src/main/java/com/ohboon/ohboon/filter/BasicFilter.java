package com.ohboon.ohboon.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter("/")
public class BasicFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        FilterConfig config = filterConfig;
        String filterName = filterConfig.getFilterName();
        System.out.println("encoding filter init call === " + filterName);
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 일반 서블릿 리퀘스트
        System.out.println("doFilter call");
        chain.doFilter(request,response); // chain 이 없으면 중간에 필터가 끊어짐. 반드시 유의할 것.
        System.out.println("jsp call after");
    }

    @Override
    public void destroy() {
        System.out.println("filter destroyed");
    }
}
