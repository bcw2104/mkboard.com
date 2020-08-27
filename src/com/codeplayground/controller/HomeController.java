package com.codeplayground.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.codeplayground.entity.UserDTO;
import com.codeplayground.serviceOthers.CategoryService;
import com.codeplayground.util.PagePath;

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
			HttpSession session = request.getSession();

			model.addAttribute("categoryList",categoryService.findList());

			if (session.getAttribute("user") == null) {
				model.addAttribute("login", null);
			} else {
				UserDTO userDTO = (UserDTO) session.getAttribute("user");
				model.addAttribute("login", userDTO.getUserId());
			}

			return "index";
		}
	}

	@GetMapping("/welcome")
	public String welcome(HttpServletRequest request, Model model) {
		model.addAttribute("requestPage",PagePath.welcomePage);
		return "forward:/";
	}

}
