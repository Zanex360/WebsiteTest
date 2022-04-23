package com.alexander.websitetest.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.alexander.websitetest.Account;
import com.alexander.websitetest.AccountRepository;
import com.alexander.websitetest.bean.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    public static Map<String, User> sessions = new HashMap<String, User>();
    
    @Autowired
    private AccountRepository repository;
    
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @GetMapping("/login")
    public String sendForm(User user) {
        return "login";
    }

    @PostMapping("/login")
    public String processForm(HttpServletResponse response, User user) throws NoSuchAlgorithmException {
        Account account = repository.findByUser(user.getUsername());
        System.out.println("!!" + account);

        if (account != null) {
            byte[] hash = account.hash;
            byte[] salt = account.salt;
            String pepper = "thisisthekey";
            String pass = user.getPassword();
            
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            md.update(pepper.getBytes());
            md.update(pass.getBytes());
            
            byte[] compare = md.digest();

            System.out.println(Arrays.toString(hash));
            System.out.println(Arrays.toString(compare));

            if (Arrays.equals(hash, compare)) {
                System.out.println("works");
                String token = getSaltString();
                Cookie cookie = new Cookie("token", token);
                cookie.setMaxAge(60);
                sessions.put(token, user);
                response.addCookie(cookie);
                return "homepage";
            }
        }
        return "loginfail";
    }
}
