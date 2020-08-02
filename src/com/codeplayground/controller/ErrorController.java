package com.codeplayground.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ErrorController
 */
@WebServlet("/error/*")
public class ErrorController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] params = request.getRequestURI().substring(7).split("/");
		if(params.length == 1) {
			if(params[0].equals("login")) {
				request.setAttribute("errorMsg", "로그인이 필요한 서비스입니다.");
				request.setAttribute("requestPage", "errorpage.jsp");
				request.getRequestDispatcher("/").forward(request, response);
			}
			else if(params[0].equals("access")){
				request.setAttribute("errorMsg", "잘못된 접근입니다.");
				request.setAttribute("requestPage", "errorpage.jsp");
				request.getRequestDispatcher("/").forward(request, response);
			}
			else if(params[0].equals("inner")){
				request.setAttribute("errorMsg", "내부적으로 오류가 발생하였습니다.");
				request.setAttribute("requestPage", "errorpage.jsp");
				request.getRequestDispatcher("/").forward(request, response);
			}
			else {
				response.sendRedirect("/");
			}
		}
		else {
			response.sendRedirect("/");
		}
	}
}
