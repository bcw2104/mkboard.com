package com.codeplayground.controller;

import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codeplayground.entity.CertificationDTO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.service.FindService;
import com.codeplayground.serviceOthers.CertificationService;
import com.codeplayground.serviceOthers.MailService;
import com.codeplayground.util.AuthTools;

@Controller
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private AuthTools authTools;
	@Autowired
	private CertificationService certificationService;
	@Autowired
	private MailService mailService;
	@Resource(name ="userFindService")
	private FindService<UserDTO> userFindService;

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
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("userEmail",userEmail);
			ArrayList<UserDTO> result = userFindService.findList(hashMap);

			if(!result.isEmpty()) {
				userId = result.get(0).getUserEmail();
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
