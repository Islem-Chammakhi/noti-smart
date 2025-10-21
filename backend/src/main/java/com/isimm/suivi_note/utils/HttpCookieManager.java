package com.isimm.suivi_note.utils;

import jakarta.servlet.http.Cookie;

public class HttpCookieManager {
    public static Cookie generateCookie(String name, String value,  int expiresInSeconds, boolean isHttpOnly){
        var c = new Cookie(name, value);
        c.setHttpOnly(isHttpOnly);
        c.setPath("/");
        c.setMaxAge(expiresInSeconds);
        return c;
    }
}
