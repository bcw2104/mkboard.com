package kr.co.codeplayground.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
	MessageDigest digest;

	public SHA256() {
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("SHA-256을 찾을 수 없습니다.");
		}
	}

	public String getMessage(String message) {
		StringBuffer hashMsg = new StringBuffer();

		digest.reset();
		try {

			digest.update(message.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("UTF-8을 지원하지 않습니다.");

			return null;
		}

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
}
