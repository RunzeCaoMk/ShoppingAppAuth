package com.cao.shoppingAppAuth.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordDecrypter {
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
