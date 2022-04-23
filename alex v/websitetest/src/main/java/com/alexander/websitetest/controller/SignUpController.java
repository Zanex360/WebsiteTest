package com.alexander.websitetest.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.alexander.websitetest.Account;
import com.alexander.websitetest.AccountRepository;
import com.alexander.websitetest.bean.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    @Autowired
    private AccountRepository repository;

    @GetMapping("/signup")
    public String sendForm(User user) {
        return "signup";
    }

    @PostMapping("/signup")
    public String processForm(User user) throws NoSuchAlgorithmException {
        if(user.getPassword().equals("")) {
            return "loginfail.html";
        }
        
        SecureRandom random = new SecureRandom();

        // Convert password to bytes
        String pass = user.getPassword();
    
        // Generate salt for new user
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        String pepper = "thisisthekey";

        // Salt and hash password
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        md.update(pepper.getBytes());
        md.update(pass.getBytes());

        byte[] hash = md.digest();
        
        repository.save(new Account(user.getUsername(), hash, salt));
        return "success.html";
    }
}