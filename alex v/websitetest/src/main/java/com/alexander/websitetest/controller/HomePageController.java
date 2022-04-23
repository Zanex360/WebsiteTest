package com.alexander.websitetest.controller;

import java.security.NoSuchAlgorithmException;

import com.alexander.websitetest.bean.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomePageController {
    
    @GetMapping("/homepage")
    public String sendForm(User user) {
        return "homepage";
    }

    @PostMapping("/homepage")
    public String processForm(User user) throws NoSuchAlgorithmException {
        return "homepage";
    }
}
