package com.codeplayground.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codeplayground.service.*;

@WebServlet("/login")
public class LoginController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoginService service = new LoginService();

		String cmd = "";

		if(request.getParameter("cmd") != null) {
			cmd = request.getParameter("cmd");
		}

		if (cmd.equals("out")) {
			service.logout(request.getSession());
			response.sendRedirect("/");
		}
		else if(cmd.equals("check")) {
			if(request.getSession().getAttribute("user") != null) {
				response.getWriter().write("true");
			}else {
				response.getWriter().write("false");
			}
		}
		else {
			request.setAttribute("requestPage", "login.html");
			request.getRequestDispatcher("/").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoginService service = new LoginService();

		String userId = request.getParameter("user_id");
		String userPw = request.getParameter("user_pw");

		if(service.login(userId, userPw,request.getSession())) {
				response.sendRedirect("/");
		}
		else {
				response.sendRedirect("login?st=fail");
		}
	}


}
