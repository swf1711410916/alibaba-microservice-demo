package com.demo.order;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class PasswordEncryptor {
    public static void main(String[] args) {
        String newPassword = "nacos"; // 替换为您的密码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("加密后的密码: " + encoder.encode(newPassword));
    }
}