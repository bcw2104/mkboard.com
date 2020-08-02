package com.codeplayground.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codeplayground.dao.UserDAO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.service.*;

@WebServlet("/user/*")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		UserService service = new UserService();

		if (path.equals("/user/register")) {
			request.setAttribute("requestPage", "register.html");
			request.getRequestDispatcher("/").forward(request, response);
		}
		else if(path.equals("/user/overlap")) {
			String userId = request.getParameter("user_id");
			String sendMsg = "";

			if (!userId.equals("")) {
				if (service.checkIdOverlap(userId)) {
					sendMsg = "true";
				} else {
					sendMsg = "false";
				}
			} else {
				sendMsg = "null";
			}

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(sendMsg);
		}
		else if (path.equals("/user/profile")) {
			request.setAttribute("requestPage", "profile.jsp");
			request.getRequestDispatcher("/").forward(request, response);
		}
		else if (path.equals("/user/admin")) {
			if (service.checkAdmin(request.getSession())) {
				int totalCount = service.getTotalCount();
				int pageNum = service.checkPage(request.getParameter("p"),totalCount);

				request.setAttribute("userList", service.getUserlist(pageNum));
				request.setAttribute("totalCount", totalCount);
				request.setAttribute("pageNum", pageNum);
				request.setAttribute("requestPage", "userlist.jsp");

				request.getRequestDispatcher("/").forward(request, response);
			} else {
				response.sendRedirect("/");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		UserService service = new UserService();

		if (path.equals("/user/register")) {

			String userId = request.getParameter("user_id");
			String userPw = request.getParameter("user_pw");
			String userName = request.getParameter("user_name");
			String userBirthList[] = request.getParameterValues("user_birth");
			String userBirth = userBirthList[0] + "-" + userBirthList[1] + "-" + userBirthList[2] + " 00:00:00";
			String userGender = request.getParameter("user_gender");
			String userPhoneList[] = request.getParameterValues("user_phone");
			String userPhone = userPhoneList[0] + "-" + userPhoneList[1] + "-" + userPhoneList[2];

			if (service.register(userId,userPw,userName,userBirth,userGender,userPhone)) {
				response.sendRedirect("/login?reg=su");
			} else {
				response.sendRedirect("/error/inner");
			}
		}
	}

}
