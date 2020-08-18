package com.codeplayground.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.codeplayground.entity.CategoryDTO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.service.FindService;

@Controller

public class HomeController {

	@Resource(name = "categoryFindService")
	private FindService<CategoryDTO> categoryFindService;

	@GetMapping("/")
	public String home(HttpServletRequest request, Model model) {
		if(request.getAttribute("requestPage")==null) {
			return "forward:/content/community";
		}
		else {
			HttpSession session = request.getSession();

			model.addAttribute("categoryList",categoryFindService.findAll());

			if (session.getAttribute("user") == null) {
				model.addAttribute("login", null);
			} else {
				UserDTO userDTO = (UserDTO) session.getAttribute("user");
				model.addAttribute("login", userDTO.getUserId());
			}

			return "index";
		}
	}

}
