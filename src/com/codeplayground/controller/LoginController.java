package com.codeplayground.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.codeplayground.entity.UserDTO;
import com.codeplayground.service.LoginService;

@Controller
@RequestMapping("/account")
public class LoginController{

	@Autowired
	private LoginService loginService;

	@GetMapping("/login")
	public String login_get(Model model) {
		model.addAttribute("requestPage", "login.jsp");

		return "forward:/";
	}

	@PostMapping("/login")
	public String login_post(@RequestParam(value = "user_id") String userId,
											@RequestParam(value = "user_pw") String userPw,
											HttpSession session,Model model) {

		if(loginService.login(userId, userPw,session)) {
			return "redirect:/";
		}
		else {
			return "redirect:/account/login?st=fail";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		loginService.logout(session);
		return "forward:/";
	}

	@GetMapping("/check")
	public void logout(@SessionAttribute("user") UserDTO userDTO,HttpServletResponse response) {
		String msg = userDTO != null ? "true" : "false";

		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
