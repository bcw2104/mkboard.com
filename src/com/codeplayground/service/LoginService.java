package com.codeplayground.service;

import javax.servlet.http.HttpSession;

import com.codeplayground.dao.UserDAO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.util.SHA256;

public class LoginService {

	public boolean login(String userId, String userPw,HttpSession session) {
		UserDAO userDAO = new UserDAO();
		SHA256 sha256 = new SHA256();

		userPw = sha256.convert(userPw);
		UserDTO userDTO = userDAO.getUser(userId);

		if (userDTO != null && userDTO.getUserPw().equals(userPw)) {
			session.setAttribute("user", userDTO);
			session.setMaxInactiveInterval(3600);
			return true;
		}
		else {
			return false;
		}
	}

	public void logout(HttpSession session) {
		if(session.getAttribute("user") != null) {
			session.removeAttribute("user");
		}
	}


}
