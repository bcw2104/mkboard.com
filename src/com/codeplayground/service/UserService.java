package com.codeplayground.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codeplayground.dao.UserDAO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.util.SHA256;

public class UserService {

	// 사용자 권한 체크
	public int check_author(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			return 0;
		}
		else {
			UserDTO userDTO = (UserDTO) session.getAttribute("user");
			if (userDTO.getUserId().equals("admin")) {
				return 1;				// 관리자
			}
			else {
				return 2;				// 일반 사용자
			}
		}
	}

	public void get_userlist(HttpServletRequest request, HttpServletResponse response) {
		if (check_author(request, response) == 1) {
			UserDAO userDAO = new UserDAO();
			int pageNum = 1;
			int totalCount = userDAO.getTotalUserCount();
			String page = request.getParameter("p");

			if (page != null) {
				try {
					pageNum = Integer.parseInt(page);

					if (!(pageNum <= (totalCount - 1) / 8 + 1 && pageNum > 0)) {
						pageNum = 1;
					}
				} catch (NumberFormatException e) {
					pageNum = 1;
				}
			}

			ArrayList<UserDTO> userList = userDAO.getUserList(pageNum);

			request.setAttribute("userList", userList);
			request.setAttribute("totalCount", totalCount);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("requestPage", "userlist.jsp");

			try {
				request.getRequestDispatcher("/").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void check_id_overlap(HttpServletRequest request, HttpServletResponse response) {
		UserDAO userDAO = new UserDAO();

		String userId = request.getParameter("user_id");

		response.setCharacterEncoding("UTF-8");

		if (userId != "") {
			UserDTO userDTO = userDAO.getUser(userId);
			if (userDTO == null) {
				try {
					response.getWriter().write("true");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					response.getWriter().write("false");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			try {
				response.getWriter().write("null");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void register(HttpServletRequest request, HttpServletResponse response) {
		UserDAO userDAO = new UserDAO();
		UserDTO userDTO = new UserDTO();
		SHA256 sha256 = new SHA256();

		String userId = request.getParameter("user_id");
		String userPw = sha256.getMessage(request.getParameter("user_pw"));
		String userName = request.getParameter("user_name");
		String userBirthList[] = request.getParameterValues("user_birth");
		String userBirth = userBirthList[0] + "-" + userBirthList[1] + "-" + userBirthList[2]+ " 00:00:00";
		String userGender = request.getParameter("user_gender");
		String userPhoneList[] = request.getParameterValues("user_phone");
		String userPhone = userPhoneList[0] + "-" + userPhoneList[1] + "-" + userPhoneList[2];

		userDTO.setUserId(userId);
		userDTO.setUserPw(userPw);
		userDTO.setUserName(userName);
		userDTO.setUserBirth(Timestamp.valueOf(userBirth));
		userDTO.setUserGender(userGender);
		userDTO.setUserPhone(userPhone);

		if (userDAO.postUser(userDTO)) {
			try {
				response.sendRedirect("/login?reg=su");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				response.sendRedirect("/user/register?st=fail");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
