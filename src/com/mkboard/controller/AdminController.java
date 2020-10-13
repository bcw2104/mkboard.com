package com.mkboard.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mkboard.serviceOthers.UserService;
import com.mkboard.util.PagePath;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@GetMapping("/members")
	public String admin(@RequestParam(required = false, value = "s") String search,
						@RequestParam(required = false, value = "p") String _pageNum,
									 HttpSession session, Model model)throws NumberFormatException{

		int totalCount = userService.getCount();
		int pageNum = 1;
		int position = 0;

		if(_pageNum != null) {
			pageNum = Integer.parseInt(_pageNum);
		}

		if(search != null && search.equals("adm") ) {
			position = 1;
		}

		model.addAttribute("type", position);
		model.addAttribute("userList", userService.findList(10 * (pageNum - 1) + 1, 10 * pageNum,position, null));
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("requestPage", PagePath.userlistPage);

		return "forward:/";

	}
}
