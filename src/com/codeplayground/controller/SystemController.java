package com.codeplayground.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codeplayground.util.PagePath;

@Controller
@RequestMapping("/system")
public class SystemController{

	@GetMapping("/error/{errorType}")
	public String error(@PathVariable String errorType, Model model) {
		String errorMsg;
		if(errorType.equals("login")) {
			errorMsg="로그인이 필요한 서비스입니다.";
		}
		else if(errorType.equals("auth")) {
			errorMsg ="해당 페이지에 대한 접근 권한이 없습니다.";
		}
		else if(errorType.equals("access")) {
			errorMsg = "잘못된 접근입니다.";
		}
		else if(errorType.equals("expiry")) {
			errorMsg = "만료된 페이지입니다.";
		}
		else {
			errorMsg ="존재하지 않는 페이지입니다.";
		}
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("requestPage", PagePath.errorPage);

		return "forward:/";
	}

}
