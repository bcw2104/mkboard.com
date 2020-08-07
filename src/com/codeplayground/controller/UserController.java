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

import com.codeplayground.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String register_get(Model model) {
		model.addAttribute("requestPage", "register.jsp");
		return "forward:/";
	}

	@GetMapping("/overlap")
	public void overlap(@RequestParam(value = "user_id") String userId,
										HttpServletResponse response, Model model) {
		String sendMsg = "";

		if (!userId.equals("")) {
			if (userService.checkIdOverlap(userId)) {
				sendMsg = "true";
			} else {
				sendMsg = "false";
			}
		} else {
			sendMsg = "null";
		}

		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(sendMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("requestPage", "profile.jsp");
		return "forward:/";
	}

	@GetMapping("/admin")
	public String admin(@RequestParam(value = "p") String requestPageNum,
									  HttpSession session, Model model) {
		if (userService.checkAdmin(session)) {
			int totalCount = userService.getTotalCount();
			int pageNum = userService.checkPage(requestPageNum,totalCount);

			model.addAttribute("userList", userService.getUserlist(pageNum));
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("pageNum", pageNum);
			model.addAttribute("requestPage", "userlist.jsp");

			return "forward:/";
		} else {
			return "redirect:/";
		}
	}

	@PostMapping("/register")
	public String register_post(@RequestParam(value = "user_id") String userId,
												 @RequestParam(value = "user_pw") String userPw,
												 @RequestParam(value = "user_name") String userName,
												 @RequestParam(value = "user_birth") String[] userBirthList,
												 @RequestParam(value = "user_gender") String userGender,
												 @RequestParam(value = "user_phone") String[] userPhoneList) {

		String userBirth = userBirthList[0] + "-" + userBirthList[1] + "-" + userBirthList[2] + " 00:00:00";
		String userPhone = userPhoneList[0] + "-" + userPhoneList[1] + "-" + userPhoneList[2];

		if (userService.register(userId,userPw,userName,userBirth,userGender,userPhone)) {
			return "redirect:/account/login?reg=su";
		}
		else {
			return "redirect:/error/inner";
		}
	}


}
