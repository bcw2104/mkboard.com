package com.codeplayground.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codeplayground.dao.CategoryDAO;
import com.codeplayground.dao.PostDAO;
import com.codeplayground.entity.PostDTO;
import com.codeplayground.entity.UserDTO;

@WebServlet("")
public class HomeController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		CategoryDAO categoryDAO = new CategoryDAO();

		request.setAttribute("categoryList",categoryDAO.getAllCategoryData());

		if (session.getAttribute("user") == null) {
			request.setAttribute("login", null);
		} else {
			UserDTO userDTO = (UserDTO) session.getAttribute("user");
			request.setAttribute("login", userDTO.getUserId());
		}
		if(request.getAttribute("requestPage")==null) {
			request.getRequestDispatcher("/content/community").forward(request, response);
		}
		else {
			request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
		}

	}
}
