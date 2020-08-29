package com.inhaplayground.serviceOthers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inhaplayground.entity.UserDTO;
import com.inhaplayground.util.AuthTools;

@Service
public class AccountService {
	@Autowired
	UserService userService;
	@Autowired
	AuthTools authTools;

	public boolean login(String userId, String userPw,HttpSession session) throws Exception{

		userPw = authTools.convertValuetoHash(userPw);
		UserDTO userDTO = userService.findOne(userId, null, null);

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
