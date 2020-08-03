package com.codeplayground.service;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.codeplayground.dao.UserDAO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.util.SHA256;

public class UserService {
	private UserDAO userDAO;

	public UserService() {
		userDAO = new UserDAO();
	}

	public int getTotalCount() {
		return userDAO.getTotalUserCount();
	}

	public int checkPage(String requestPageNum, int totalCount) {
		int pageNum = 1;

		if (requestPageNum != null) {
			try {
				pageNum = Integer.parseInt(requestPageNum);

				if (!(pageNum <= (totalCount - 1) / 8 + 1 && pageNum > 0)) {
					pageNum = 1;
				}
			} catch (NumberFormatException e) {
				pageNum = 1;
			}
		}

		return pageNum;
	}

	// 사용자 권한 체크
	public boolean checkAdmin(HttpSession session) {
		UserDTO userDTO = (UserDTO) session.getAttribute("user");

		if (userDTO != null) {
			if (userDTO.getUserId().equals("admin")) {
				return true; // 관리자
			}
		}
		return false;
	}

	public ArrayList<UserDTO> getUserlist(int pageNum) {
		ArrayList<UserDTO> list = userDAO.getUserList(pageNum);

		return list;
	}

	public boolean checkIdOverlap(String userId) {
		UserDTO userDTO = userDAO.getUser(userId);

		if (userDTO == null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean register(String userId,String userPw,String userName,String userBirth,String userGender,String userPhone) {
		UserDTO userDTO = new UserDTO();
		SHA256 sha256 = new SHA256();

		userDTO.setUserId(userId);
		userDTO.setUserPw(sha256.convert(userPw));
		userDTO.setUserName(userName);
		userDTO.setUserBirth(Timestamp.valueOf(userBirth));
		userDTO.setUserGender(userGender);
		userDTO.setUserPhone(userPhone);

		return userDAO.postUser(userDTO);
	}
}
