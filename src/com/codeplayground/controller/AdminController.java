package com.codeplayground.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codeplayground.service.UserService;
import com.codeplayground.util.PagePath;
import com.codeplayground.util.Tools;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;
	@Autowired
	Tools tools;

	@GetMapping("/users")
	public String admin(@RequestParam(required = false, value = "p") String requestPageNum,
									 HttpSession session, Model model)throws Exception{

		int totalCount = userService.getTotalCount();
		int pageNum = tools.checkPage(requestPageNum, totalCount,8);

		model.addAttribute("userList", userService.getUserlist(pageNum));
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("requestPage", PagePath.userlistPage);

		return "forward:/";

	}
}
