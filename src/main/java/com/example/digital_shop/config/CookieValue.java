package com.example.digital_shop.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieValue {
    public static String getValue(String name, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(name)){
                return cookie.getValue();
            }
        }
        return "null";
    }
}
