package com.alexander.websitetest.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alexander.websitetest.bean.User;

import org.springframework.stereotype.Component;

@Component
public class ServletFilter implements Filter {
    
    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Cookie[] cookies = httpRequest.getCookies();
        if (httpRequest.getRequestURI().equals("/homepage")) {
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    if(cookies[i].getName().equals("token")) {
                        if (LoginController.sessions.get(cookies[i].getValue()) != null) {
                            User s = LoginController.sessions.get(cookies[i].getValue());
                            System.out.println(s.getUsername());
                            chain.doFilter(request, response);
                            return;
                        }
                    }
                }
            }
            else {
                httpResponse.sendRedirect("/login");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {}
}
