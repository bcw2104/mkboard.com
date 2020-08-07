package com.codeplayground.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController{

	@GetMapping("/{errorType}")
	public String error(@PathVariable String errorType, Model model) {
		String errorMsg;
		if(errorType.equals("login")) {
			errorMsg="로그인이 필요한 서비스입니다.";
		}
		else if(errorType.equals("access")) {
			errorMsg ="잘못된 접근입니다.";
		}
		else {
			errorMsg = "내부적으로 오류가 발생하였습니다.";
		}
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("requestPage", "errorpage.jsp");

		return "forward:/";
	}

}
