package com.inhaplayground.controller;

import java.io.InputStreamReader;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inhaplayground.entity.CertificationDTO;
import com.inhaplayground.entity.UserDTO;
import com.inhaplayground.serviceOthers.CertificationService;
import com.inhaplayground.serviceOthers.MailService;
import com.inhaplayground.serviceOthers.UserService;
import com.inhaplayground.util.AuthTools;

@Controller
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private AuthTools authTools;
	@Autowired
	private CertificationService certificationService;
	@Autowired
	private MailService mailService;
	@Autowired
	private UserService userService;

	@PostMapping("/modify")
	public void modify(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(new InputStreamReader(request.getInputStream(),"UTF-8"));

		String userEmail = object.get("user_email").toString();
		String key = authTools.makeAuthCode();

		mailService.sendModifyMail(userEmail,key);
		CertificationDTO certificationDTO = new CertificationDTO();
		certificationDTO.setUserEmail(userEmail);
		certificationDTO.setKey(authTools.convertValuetoHash(userEmail+key));
		certificationDTO.setExpiry(new Timestamp(System.currentTimeMillis()+(long)(1000*60*15)));

		certificationService.insert(certificationDTO);
	}

	@PostMapping("/register")
	public void register(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(new InputStreamReader(request.getInputStream(),"UTF-8"));

		String userEmail = object.get("user_email").toString();
		String key = authTools.makeAuthCode();

		mailService.sendRegisterMail(userEmail,key);
		CertificationDTO certificationDTO = new CertificationDTO();
		certificationDTO.setUserEmail(userEmail);
		certificationDTO.setKey(authTools.convertValuetoHash(userEmail+key));
		certificationDTO.setExpiry(new Timestamp(System.currentTimeMillis()+(long)(1000*60*15)));

		certificationService.insert(certificationDTO);
	}

	@PostMapping("/find")
	public void send(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(new InputStreamReader(request.getInputStream(),"UTF-8"));

		String userEmail = object.get("user_email").toString();
		String key = authTools.convertValuetoHash(userEmail+authTools.makeAuthCode());
		String userId = null;

		if(request.getSession().getAttribute("tempUserId") == null) {
			UserDTO userDTO = userService.findOne(null, null, userEmail);

			if(userDTO != null) {
				userId = userDTO.getUserId();
			}
		}else {
			userId = request.getSession().getAttribute("tempUserId").toString();
			request.getSession().removeAttribute("tempUserId");
		}

		if(userId != null) {
			mailService.sendFindMail(userEmail,userId,key);
			CertificationDTO certificationDTO = new CertificationDTO();
			certificationDTO.setUserEmail(userEmail);
			certificationDTO.setKey(key);
			certificationDTO.setExpiry(new Timestamp(System.currentTimeMillis()+(long)(1000*60*15)));

			certificationService.insert(certificationDTO);
		}
	}

}
