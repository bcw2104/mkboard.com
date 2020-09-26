package com.mkboard.controller;

import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mkboard.entity.UserDTO;
import com.mkboard.service.ModifyService;
import com.mkboard.serviceOthers.CommentService;
import com.mkboard.serviceOthers.PostFileService;
import com.mkboard.serviceOthers.PostService;
import com.mkboard.util.AuthTools;
import com.mkboard.util.FileTools;
import com.mkboard.util.PagePath;

@Controller
@RequestMapping("/user")
public class UserController {

	@Value("${file.user.profile}")
	private String profilePath;

	@Autowired
	private FileTools fileTools;
	@Autowired
	private AuthTools authTools;
	@Autowired
	private CommentService commentService;
	@Autowired
	private PostService postService;
	@Autowired
	private PostFileService postFileService;

	@Resource(name ="userModifyService")
	private ModifyService<UserDTO> userModifyService;



	@ExceptionHandler(java.lang.NumberFormatException.class)
	public String exception() {
		return "redirect:/system/error/none";
	}

	@GetMapping("/profile")
	public String profile(@RequestParam(required = false, value="p") String _pageNum,
										@SessionAttribute(name = "user") UserDTO userDTO ,Model model) throws NumberFormatException {
		int pageNum = 1;
		if(_pageNum != null) {
			pageNum = Integer.parseInt(_pageNum);
		}
		int postCount = postService.getCount(null, null, 0, null, userDTO.getUserId(), true);

		model.addAttribute("postList", postService.findList(null, null, 0, null, userDTO.getUserId(),
																				true, null, 8 * (pageNum - 1) + 1, 8 * pageNum));

		int commentCount = commentService.getCount(0,userDTO.getUserId())+commentService.getSubCount(0,userDTO.getUserId());

		model.addAttribute("pageNum",pageNum);
		model.addAttribute("postCount",postCount);
		model.addAttribute("commentCount",commentCount);
		model.addAttribute("score",(2*commentCount+10*postCount));
		model.addAttribute("userNickName", userDTO.getUserNickName());
		model.addAttribute("userId", userDTO.getUserId());

		model.addAttribute("requestPage", PagePath.profilePage);

		return "forward:/";
	}

	@GetMapping("/modify")
	public String modify_get(Model model) throws Exception {
		model.addAttribute("requestPage", PagePath.modifyPage);

		return "forward:/";
	}

	@PostMapping("/modify")
	public void modify_post(MultipartHttpServletRequest mtfRequest,
											@RequestParam(value = "user_id") String userId,
											@RequestParam(value = "user_nick_name") String userNickName,
											 @RequestParam(value = "user_name") String userName,
											 @RequestParam(value = "user_email") String userEmail,
											 @RequestParam(value = "user_birth") String[] userBirthList,
											 @RequestParam(value = "user_phone") String[] userPhoneList,
											 @SessionAttribute("user") UserDTO userDTO,HttpServletResponse response) throws Exception{

		if(userDTO.getUserId().equals(userId)) {
			String userBirth = userBirthList[0] + "-" + userBirthList[1] + "-" + userBirthList[2]+" 00:00:00";
			String userPhone = userPhoneList[0] + "-" + userPhoneList[1] + "-" + userPhoneList[2];

			userDTO.setUserNickName(userNickName);
			userDTO.setUserName(userName);
			userDTO.setUserEmail(userEmail);
			userDTO.setUserBirth(Timestamp.valueOf(userBirth));
			userDTO.setUserPhone(userPhone);

			userModifyService.update(userDTO);

			MultipartFile multipartFile = mtfRequest.getFile("upload_file");

			if(multipartFile != null) {
				String fileName = multipartFile.getOriginalFilename();
				String extension = fileName.substring(fileName.lastIndexOf(".")+1);
				fileTools.fileRemover(userId,0, profilePath);

				fileTools.createFile(userId, 0, "profile."+extension,multipartFile);
			}


			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write( "<script>"
														+ "alert('회원정보가 변경되었습니다.');"
														+ "window.location.href='/user/profile';"
														+ "</script>");
		}
		else {
			response.sendRedirect("/system/error/auth");
		}
	}

	@GetMapping("/password")
	public String password_get(Model model) throws Exception{
		model.addAttribute("requestPage", PagePath.passwordPage);

		return "forward:/";
	}

	@PostMapping("/password")
	public void password_post(@RequestParam(value = "old_user_pw") String oldUserPw,
												@RequestParam(value = "new_user_pw") String newUserPw,
												@SessionAttribute("user") UserDTO userDTO,
												HttpServletResponse response) throws Exception{

		String msg;
		String url;
		String hashedOldUserPw = authTools.convertValuetoHash(oldUserPw);

		if(userDTO.getUserPw().equals(hashedOldUserPw)) {
			userDTO.setUserPw(authTools.convertValuetoHash(newUserPw));
			userModifyService.update(userDTO);
			msg = "비밀번호가 변경되었습니다.";
			url = "/user/profile";
		}else {
			msg = "기존 비밀번호가 일치하지 않습니다.";
			url = "/user/password";
		}

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write( "<script>"
													+ "alert('"+msg+"');"
													+ "window.location.href='"+url+"';"
													+ "</script>");

	}

	@GetMapping("/leave")
	public String leave_get(Model model) throws Exception{
		model.addAttribute("requestPage", PagePath.leavePage);

		return "forward:/";
	}

	@PostMapping("/leave")
	public void leave_post(@RequestParam(value = "user_pw") String userPw,
												@SessionAttribute("user") UserDTO userDTO,
												HttpServletResponse response) throws Exception{

		String msg;
		String url;
		String hashedUserPw = authTools.convertValuetoHash(userPw);

		if(userDTO.getUserPw().equals(hashedUserPw)) {
			userModifyService.delete(userDTO.getUserId());
			postFileService.delete(userDTO.getUserId(), 0, null);
			msg = "회원탈퇴가 완료되었습니다.";
			url = "/account/logout";
		}else {
			msg = "비밀번호가 일치하지 않습니다.";
			url = "/user/leave";
		}

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write( "<script>"
													+ "alert('"+msg+"');"
													+ "window.location.href='"+url+"';"
													+ "</script>");

	}
}
