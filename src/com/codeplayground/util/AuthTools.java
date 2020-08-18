package com.codeplayground.util;

import java.util.Random;

public class AuthTools {

	public String makeAuthCode() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();
			for (int i = 0; i < 10; i++) {
				int rIndex = rnd.nextInt(3);
				switch (rIndex) {
				case 0:
					// a-z
					key.append((char) ((int) (rnd.nextInt(26)) + 97));
					break;
				case 1:
					// A-Z
					key.append((char) ((int) (rnd.nextInt(26)) + 65));
					break;
				case 2:
					// 0-9
					key.append((rnd.nextInt(10)));
					break;
				}
			}

		return key.toString();
	}
}
