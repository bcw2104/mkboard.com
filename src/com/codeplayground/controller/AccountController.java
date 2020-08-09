package com.codeplayground.controller;

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
import com.codeplayground.service.AccountService;
import com.codeplayground.util.PagePath;

@Controller
@RequestMapping("/account")
public class AccountController{

	@Autowired
	private AccountService accountService;

	@GetMapping("/login")
	public String login_get(Model model) {
		model.addAttribute("requestPage", PagePath.loginPage);

		return "forward:/";
	}

	@PostMapping("/login")
	public String login_post(@RequestParam(value = "user_id") String userId,
											@RequestParam(value = "user_pw") String userPw,
											HttpSession session,Model model) throws Exception{

		if(accountService.login(userId, userPw,session)) {
			return "redirect:/";
		}
		else {
			return "redirect:/account/login?st=fail";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		accountService.logout(session);
		return "redirect:/";
	}

	@GetMapping("/check")
	public void check(@SessionAttribute(value = "user", required = false) UserDTO userDTO,
									HttpServletResponse response) throws Exception{
		String msg = (userDTO != null ? "true" : "false");

		response.getWriter().write(msg);
	}

	@GetMapping("/register")
	public String register_get(Model model) {
		model.addAttribute("requestPage", PagePath.registerPage);
		return "forward:/";
	}

	@PostMapping("/register")
	public String register_post(@RequestParam(value = "user_id") String userId,
												 @RequestParam(value = "user_pw") String userPw,
												 @RequestParam(value = "user_name") String userName,
												 @RequestParam(value = "user_birth") String[] userBirthList,
												 @RequestParam(value = "user_gender") String userGender,
												 @RequestParam(value = "user_phone") String[] userPhoneList) throws Exception{

		String userBirth = userBirthList[0] + "-" + userBirthList[1] + "-" + userBirthList[2] + " 00:00:00";
		String userPhone = userPhoneList[0] + "-" + userPhoneList[1] + "-" + userPhoneList[2];

		accountService.register(userId,userPw,userName,userBirth,userGender,userPhone);

		return "redirect:/account/login?reg=su";
	}

	@GetMapping("/overlap")
	public void overlap(@RequestParam(value = "user_id") String userId,
										HttpServletResponse response, Model model) throws Exception{
		String sendMsg = "";

		if (!userId.equals("")) {
			if (accountService.checkIdOverlap(userId)) {
				sendMsg = "true";
			} else {
				sendMsg = "false";
			}
		} else {
			sendMsg = "null";
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(sendMsg);
	}

}
