package com.codeplayground.util;

import java.security.MessageDigest;

import javax.servlet.http.Cookie;

public class Tools {

	public String convertValuetoHash(String message) throws Exception {
		MessageDigest digest= MessageDigest.getInstance("SHA-256");
		StringBuffer hashMsg = new StringBuffer();

		digest.reset();

		digest.update(message.getBytes("UTF-8"));

		byte[] hash = digest.digest();

		for(int i=0; i<hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);

			if(hex.length() == 1) {
				hashMsg.append(0);
			}
			hashMsg.append(hex);
		}

		return hashMsg.toString();
	}

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
