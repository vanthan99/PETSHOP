package com.cdio.petshop;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "than";
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        System.out.println(encodedPassword);
    }
}
