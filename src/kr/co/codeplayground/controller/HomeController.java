package kr.co.codeplayground.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.codeplayground.database.PostDAO;
import kr.co.codeplayground.database.PostDTO;
import kr.co.codeplayground.database.UserDTO;

@WebServlet("")
public class HomeController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

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
