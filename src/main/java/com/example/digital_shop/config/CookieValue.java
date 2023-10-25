package com.example.digital_shop.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

public class CookieValue {
    public static String getValue(String name, HttpServletRequest request) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    if (StringUtils.isNotBlank(cookie.getValue())) {
                        return cookie.getValue();
                    }
                }
            }
            return null;
    }
}
