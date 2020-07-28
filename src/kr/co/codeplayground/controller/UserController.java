package kr.co.codeplayground.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.codeplayground.service.*;

@WebServlet("/user/*")
public class UserController extends HttpServlet {

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

		if (request.getParameter("cmd") != null) {
			cmd = request.getParameter("cmd");
		}

		String receiver = request.getRequestURI();
		UserService service = new UserService();

		if (receiver.equals("/user/register")) {
			if (cmd.equals("cio")) {
				service.check_id_overlap(request, response);
			}
			else if (cmd.equals("submit")) {
				service.register(request, response);
			}
			else {
				request.setAttribute("requestPage", "register.html");
				request.getRequestDispatcher("/").forward(request, response);
			}
		}
		else if (receiver.equals("/user/profile")) {
			request.setAttribute("requestPage", "profile.jsp");
			request.getRequestDispatcher("/").forward(request, response);
		}
		else if (receiver.equals("/user/admin")) {
			service.get_userlist(request, response);
		}
	}

}
