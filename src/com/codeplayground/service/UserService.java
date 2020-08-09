package com.codeplayground.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.codeplayground.dao.UserDAO;
import com.codeplayground.entity.UserDTO;

public class UserService {
	private UserDAO userDAO;

	public UserService() {
		userDAO = new UserDAO();
	}

	public int getTotalCount() throws SQLException{
		return userDAO.getTotalUserCount();
	}


	public ArrayList<UserDTO> getUserlist(int pageNum) throws SQLException{
		ArrayList<UserDTO> list = userDAO.getUserList(pageNum);

		return list;
	}
}
