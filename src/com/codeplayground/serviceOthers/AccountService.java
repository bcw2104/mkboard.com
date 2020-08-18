package com.codeplayground.serviceOthers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeplayground.entity.UserDTO;
import com.codeplayground.mapper.UserMapperInterface;
import com.codeplayground.util.Tools;

@Service
public class AccountService {
	@Autowired
	UserMapperInterface mapper;
	@Autowired
	Tools tools;

	public boolean login(String userId, String userPw,HttpSession session) throws Exception{

		userPw = tools.convertValuetoHash(userPw);
		UserDTO userDTO = mapper.selectOnebyId(userId);

		if (userDTO != null && userDTO.getUserPw().equals(userPw)) {
			session.setAttribute("user", userDTO);
			session.setMaxInactiveInterval(3600);
			return true;
		}
		else {
			return false;
		}
	}

	public void logout(HttpSession session) {
		session.removeAttribute("user");
	}

}