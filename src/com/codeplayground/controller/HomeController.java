package com.codeplayground.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.codeplayground.dao.CategoryDAO;
import com.codeplayground.entity.UserDTO;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();

		CategoryDAO categoryDAO = new CategoryDAO();

		model.addAttribute("categoryList",categoryDAO.getAllCategoryData());

		if (session.getAttribute("user") == null) {
			model.addAttribute("login", null);
		} else {
			UserDTO userDTO = (UserDTO) session.getAttribute("user");
			model.addAttribute("login", userDTO.getUserId());
		}
		if(request.getAttribute("requestPage")==null) {
			return "forward:/content/community";
		}
		else {
			return "index";
		}
	}

}
