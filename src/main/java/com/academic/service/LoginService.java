package com.academic.service;

public class LoginService {

    public boolean login(String username, String password) {
        return "admin".equals(username) && "admin123".equals(password);
    }
}