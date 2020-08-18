package com.codeplayground.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codeplayground.util.PagePath;

@Controller
@RequestMapping("/user")
public class UserController {


	@GetMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("requestPage", PagePath.profilePage);
		return "forward:/";
	}
}
