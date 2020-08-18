package com.codeplayground.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codeplayground.util.Tools;

@Controller
@RequestMapping("/util")
public class UtilController {

	@Autowired
	Tools tools;

	@GetMapping("/cookie")
	public void cookie(@RequestParam(name = "n") String name,
								    @RequestParam(name = "v") String value,
									HttpServletRequest request,HttpServletResponse response) {
		Cookie cookie = tools.getCookie(request.getCookies(), name);

		if(cookie != null) {
			cookie.setMaxAge(0);
		}
		cookie = tools.setCookie(name, value, "/", -1);
		response.addCookie(cookie);
	}
}
