package com.codeplayground.util;

import javax.servlet.http.Cookie;

public class CookieTools {

	public Cookie setCookie(String name, String value,String path, int expiry) {
		Cookie cookie = new Cookie(name,value);
		cookie.setPath(path);
		cookie.setMaxAge(expiry);

		return cookie;
	}

	public Cookie getCookie(Cookie[] cookies,String name) {
		Cookie result = null;

		for(Cookie cookie:cookies) {
			if(cookie.getName().equals(name)) {
				result = cookie;
				break;
			}
		}

		return result;
	}
}
