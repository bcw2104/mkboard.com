package com.mkboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mkboard.serviceOthers.CategoryService;
import com.mkboard.util.PagePath;

@Controller
public class HomeController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/")
	public String home(HttpServletRequest request, Model model) {
		if(request.getAttribute("requestPage")==null) {
			return "forward:/content/community";
		}
		else {
			model.addAttribute("categoryList",categoryService.findList());
			return "index";
		}
	}

	@GetMapping("/welcome")
	public String welcome(HttpServletRequest request, Model model) {
		model.addAttribute("requestPage",PagePath.welcomePage);
		return "forward:/";
	}

}
