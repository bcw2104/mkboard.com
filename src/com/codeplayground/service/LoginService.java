package com.codeplayground.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codeplayground.database.UserDAO;
import com.codeplayground.database.UserDTO;
import com.codeplayground.util.SHA256;

public class LoginService {

	public LoginService() {}

	public void login(HttpServletRequest request, HttpServletResponse response) {
		UserDAO userDAO = new UserDAO();
		SHA256 sha256 = new SHA256();

		HttpSession session = request.getSession();

		String userId = request.getParameter("user_id");
		String userPw = sha256.getMessage(request.getParameter("user_pw"));
		UserDTO userDTO = userDAO.getUser(userId);

		if (userDTO != null && userDTO.getUserPw().equals(userPw)) {
			session.setAttribute("user", userDTO);
			session.setMaxInactiveInterval(600);
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				response.sendRedirect("login?st=fail");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();

		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
