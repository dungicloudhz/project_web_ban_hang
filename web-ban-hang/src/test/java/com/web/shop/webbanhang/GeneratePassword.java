package com.web.shop.webbanhang;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String codejava = "codejava";
        String encodejava = encoder.encode(codejava);

        System.out.println(encodejava);
    }
}
