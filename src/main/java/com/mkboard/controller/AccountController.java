package com.mkboard.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.mkboard.entity.CertificationDTO;
import com.mkboard.entity.UserDTO;
import com.mkboard.service.ModifyService;
import com.mkboard.serviceOthers.AccountService;
import com.mkboard.serviceOthers.CertificationService;
import com.mkboard.serviceOthers.UserService;
import com.mkboard.util.AuthTools;
import com.mkboard.util.PagePath;

@Controller
@RequestMapping("/account")
public class AccountController{

	@Autowired
	private AuthTools authTools;
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CertificationService certificationService;
	@Resource(name ="userModifyService")
	private ModifyService<UserDTO> userModifyService;

	@GetMapping("/login")
	public String login_get(Model model) {
		model.addAttribute("requestPage", PagePath.loginPage);

		return "forward:/";
	}

	@PostMapping("/login")
	public void login_post(@RequestParam(value = "user_id") String userId,
											@RequestParam(value = "user_pw") String userPw,
											HttpSession session,HttpServletResponse response) throws Exception{

		if(accountService.login(userId, userPw,session)) {
			response.sendRedirect("/");
		}
		else {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write( "<script>"
														+ "alert('아이디 또는 비밀번호가 일치하지 않습니다.');"
														+ "window.location.href='/account/login';"
														+ "</script>");
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
												 @RequestParam(value = "user_nick_name") String userNickName,
												 @RequestParam(value = "user_name") String userName,
												 @RequestParam(value = "user_email") String userEmail,
												 @RequestParam(value = "user_birth") String[] userBirthList,
												 @RequestParam(value = "user_gender") String userGender,
												 @RequestParam(value = "user_phone") String[] userPhoneList) throws Exception{

		String userBirth = userBirthList[0] + "-" + userBirthList[1] + "-" + userBirthList[2]+" 00:00:00";
		String userPhone = userPhoneList[0] + "-" + userPhoneList[1] + "-" + userPhoneList[2];

		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setUserNickName(userNickName);
		userDTO.setUserName(userName);
		userDTO.setUserPw(authTools.convertValuetoHash(userPw));
		userDTO.setUserEmail(userEmail);
		userDTO.setUserBirth(Timestamp.valueOf(userBirth));
		userDTO.setUserGender(userGender);
		userDTO.setUserPhone(userPhone);

		userModifyService.register(userDTO);


		return "redirect:/welcome";
	}

	@GetMapping("/find")
	public String find_get(Model model) {
		model.addAttribute("requestPage", PagePath.findPage);
		return "forward:/";
	}

	@GetMapping("/find/{key}")
	public String find_get(@PathVariable String key,
										HttpSession session,Model model) {


		CertificationDTO certificationDTO = certificationService.findOne(null, key);

		if(certificationDTO != null) {
			UserDTO userDTO = userService.findOne(null, null, certificationDTO.getUserEmail());

			session.setAttribute("tempUser", userDTO);

			model.addAttribute("requestPage", PagePath.findChangePage);
			return "forward:/";
		}else {
			return "redirect:/system/error/expiry";
		}
	}

	@GetMapping("/find/complete")
	public String complete(Model model) {
		model.addAttribute("requestPage", PagePath.findCompletePage);
		return "forward:/";
	}

	@PostMapping("/change")
	public void change(@RequestParam(value = "user_pw") String userPw,
										@SessionAttribute("tempUser") UserDTO userDTO,
										HttpServletResponse response)  throws Exception{

		if(userDTO != null) {
			userDTO.setUserPw(authTools.convertValuetoHash(userPw));
			userModifyService.update(userDTO);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write( "<script>"
														+ "alert('비밀번호가 변경되었습니다.');"
														+ "window.location.href='/account/login';"
														+ "</script>");
		}
		else {
			response.sendError(500);
		}
	}

	@PostMapping("/certification")
	public void certification(HttpServletRequest request,HttpServletResponse response)  throws Exception{
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(new InputStreamReader(request.getInputStream(),"UTF-8"));

		String userEmail = object.get("user_email").toString();
		String key = object.get("val").toString();

		CertificationDTO certificationDTO = certificationService.findOne(userEmail,null);
		String sendMsg;
		if(certificationDTO != null && certificationDTO.getKey().equals(authTools.convertValuetoHash(userEmail+key))) {
			sendMsg = "true";
		}else {
			sendMsg = "false";
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(sendMsg);

	}

	@PostMapping("/overlap")
	public void overlap(@RequestParam(required = false) String cmd,
									HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(new InputStreamReader(request.getInputStream(),"UTF-8"));

		boolean status = true;
		String name = object.get("name").toString();
		String value = object.get("value").toString();
		UserDTO userDTO = null;

		if(name.equals("userEmail")) {
			userDTO= userService.findOne(null, null, value);
		}
		else if(name.equals("userId")) {
			userDTO= userService.findOne(value, null, null);
		}
		else if(name.equals("userNickName")){
			userDTO= userService.findOne(null, value, null);
		}
		else {
			status = false;
		}

		if(status) {
			String sendMsg;

			if (userDTO == null) {
				sendMsg = "true";
			} else {
				if(cmd != null && cmd.equals("send")) {
					request.getSession().setAttribute("tempUserId", userDTO.getUserId());
				}
				sendMsg = "false";
			}

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(sendMsg);
		}
		else {
			response.sendError(500);
		}

	}

}
