package com.codeplayground.service;

import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import com.codeplayground.dao.UserDAO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.util.SHA256;

public class AccountService {
	private UserDAO userDAO;

	public AccountService() {
		userDAO = new UserDAO();
	}

	public boolean login(String userId, String userPw,HttpSession session) throws SQLException{
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
		session.removeAttribute("user");
	}

	public boolean checkIdOverlap(String userId) throws SQLException{
		UserDTO userDTO = userDAO.getUser(userId);

		return userDTO == null;
	}

	public void register(String userId,String userPw,String userName,
			String userBirth,String userGender,String userPhone) throws SQLException{

		UserDTO userDTO = new UserDTO();
		SHA256 sha256 = new SHA256();

		userDTO.setUserId(userId);
		userDTO.setUserPw(sha256.convert(userPw));
		userDTO.setUserName(userName);
		userDTO.setUserBirth(Timestamp.valueOf(userBirth));
		userDTO.setUserGender(userGender);
		userDTO.setUserPhone(userPhone);

		userDAO.postUser(userDTO);
	}


}
