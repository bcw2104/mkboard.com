package com.codeplayground.controller;

import java.io.InputStreamReader;
import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.codeplayground.entity.CertificationDTO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.service.FindService;
import com.codeplayground.service.ModifyService;
import com.codeplayground.serviceOthers.AccountService;
import com.codeplayground.serviceOthers.CertificationService;
import com.codeplayground.serviceOthers.MailService;
import com.codeplayground.util.AuthTools;
import com.codeplayground.util.PagePath;
import com.codeplayground.util.Tools;

@Controller
@RequestMapping("/account")
public class AccountController{

	@Autowired
	private Tools tools;
	@Autowired
	private AuthTools authTools;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CertificationService certificationService;
	@Autowired
	private MailService mailService;
	@Resource(name ="userModifyService")
	private ModifyService<UserDTO> userModifyService;
	@Resource(name ="userFindService")
	private FindService<UserDTO> userFindService;

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
												 @RequestParam(value = "user_email") String userEmail,
												 @RequestParam(value = "user_birth") String[] userBirthList,
												 @RequestParam(value = "user_gender") String userGender,
												 @RequestParam(value = "user_phone") String[] userPhoneList) throws Exception{

		String userBirth = userBirthList[0] + "-" + userBirthList[1] + "-" + userBirthList[2]+" 00:00:00";
		String userPhone = userPhoneList[0] + "-" + userPhoneList[1] + "-" + userPhoneList[2];

		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setUserName(userName);
		userDTO.setUserPw(tools.convertValuetoHash(userPw));
		userDTO.setUserEmail(userEmail);
		userDTO.setUserBirth(Timestamp.valueOf(userBirth));
		userDTO.setUserGender(userGender);
		userDTO.setUserPhone(userPhone);

		userModifyService.register(userDTO);


		return "redirect:/welcome";
	}

	@PostMapping("/certification")
	public void certification(HttpServletRequest request,HttpServletResponse response)  throws Exception{
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(new InputStreamReader(request.getInputStream(),"UTF-8"));

		String userEmail = object.get("user_email").toString();
		String key = object.get("val").toString();

		CertificationDTO certificationDTO = certificationService.findOnebyEmail(userEmail);
		String sendMsg;
		if(certificationDTO != null && certificationDTO.getKey().equals(tools.convertValuetoHash(key))) {
			sendMsg = "true";
		}else {
			sendMsg = "false";
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(sendMsg);

	}

	@PostMapping("/reqkey")
	public void reqkey(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(new InputStreamReader(request.getInputStream(),"UTF-8"));

		String userEmail = object.get("user_email").toString();
		String key = authTools.makeAuthCode();
		String sendMsg;

		if(mailService.sendRegisterMail(userEmail,key)) {
			CertificationDTO certificationDTO = new CertificationDTO();
			certificationDTO.setUserEmail(userEmail);
			certificationDTO.setKey(tools.convertValuetoHash(key));
			certificationDTO.setExpiry(new Timestamp(System.currentTimeMillis()+(long)(1000*60*15)));

			certificationService.insert(certificationDTO);

			sendMsg = "true";
		}else {
			sendMsg = "false";
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(sendMsg);
	}

	@GetMapping("/overlap")
	public void overlap(@RequestParam(value = "user_id") String userId,
										HttpServletResponse response, Model model) throws Exception{
		String sendMsg;

		if (!userId.equals("")) {
			if (userFindService.findOnebyKey(userId) == null) {
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
