package kr.co.codeplayground.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.codeplayground.service.*;

@WebServlet("/login")
public class LoginController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionPostDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionPostDo(request, response);
	}

	private void actionPostDo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cmd = "";

		if(request.getParameter("cmd") != null) {
			cmd = request.getParameter("cmd");
		}

		LoginService service = new LoginService();

		if (cmd.equals("in")) {
			service.login(request, response);
		}
		else if (cmd.equals("out")) {
			service.logout(request, response);
		}
		else {
			request.setAttribute("requestPage", "login.html");
			request.getRequestDispatcher("/").forward(request, response);
		}

	}

}
