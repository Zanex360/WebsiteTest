package com.alexander.websitetest.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogOutController {

    @GetMapping("/logout")
    public void sendForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie[] allCookies = request.getCookies();
        if (allCookies != null) {
            for (int i = 0; i < allCookies.length; i++) {
                allCookies[i].setMaxAge(0);
                LoginController.sessions.clear();
                response.addCookie(allCookies[i]);
            }
        }
        response.sendRedirect("login");
    }
}
