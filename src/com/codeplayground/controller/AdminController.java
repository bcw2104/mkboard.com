package com.codeplayground.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codeplayground.entity.UserDTO;
import com.codeplayground.service.FindService;
import com.codeplayground.serviceOthers.UserOtherService;
import com.codeplayground.util.PagePath;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserOtherService userOtherService;
	@Resource(name = "userFindService")
	FindService<UserDTO> userFindService;

	@GetMapping("/members")
	public String admin(@RequestParam(required = false, value = "p") String _pageNum,
									 HttpSession session, Model model)throws NumberFormatException{

		int totalCount = userOtherService.getTotalCount();
		int pageNum = 1;

		if(_pageNum != null) {
			pageNum = Integer.parseInt(_pageNum);
		}

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("frontPageNum", 10 * (pageNum - 1) + 1);
		hashMap.put("rearPageNum", 10 * pageNum);

		model.addAttribute("userList", userFindService.findList(hashMap));
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("requestPage", PagePath.userlistPage);

		return "forward:/";

	}
}
