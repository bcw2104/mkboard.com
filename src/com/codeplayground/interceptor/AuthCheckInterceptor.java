package com.codeplayground.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.codeplayground.entity.UserDTO;

public class AuthCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");

		if (userDTO != null && userDTO.getUserId().equals("admin")) {
			return true; // 관리자
		}else{
			response.sendRedirect("/system/error/auth");
			return false;
		}
	}

}
