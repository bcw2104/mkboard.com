package com.codeplayground.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(java.lang.Exception.class)
	public String exception() {
		return "redirect:/error/inner";
	}
}
