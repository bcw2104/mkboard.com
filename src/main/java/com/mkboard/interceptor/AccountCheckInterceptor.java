package com.mkboard.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.mkboard.entity.UserDTO;

public class AccountCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");

		if(userDTO != null) {
			return true;
		}
		else {
			response.sendRedirect("/system/error/login");
			return false;
		}
	}
}
