package com.codeplayground.controller;

import java.sql.Timestamp;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.codeplayground.entity.CommentDTO;
import com.codeplayground.entity.PostDTO;
import com.codeplayground.entity.SubCommentDTO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.service.FindService;
import com.codeplayground.service.ModifyService;
import com.codeplayground.serviceOthers.PostOtherService;
import com.codeplayground.util.FileTools;
import com.codeplayground.util.PagePath;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private FileTools fileTools;
	@Autowired
	private PostOtherService postOtherService;

	@Resource(name ="userModifyService")
	private ModifyService<UserDTO> userModifyService;
	@Resource(name = "postFindService")
	private FindService<PostDTO> postFindService;
	@Resource(name = "commentFindService")
	private FindService<CommentDTO> commentFindService;
	@Resource(name = "subCommentFindService")
	private FindService<SubCommentDTO> subCommentFindService;


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

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("correct", true);
		hashMap.put("userId", userDTO.getUserId());

		int postCount = postFindService.getCount(hashMap);

		hashMap.put("frontPageNum", 8 * (pageNum - 1) + 1);
		hashMap.put("rearPageNum", 8 * pageNum);
		model.addAttribute("postList", postOtherService.findListjoinBoard(hashMap));

		hashMap.clear();
		hashMap.put("userId", userDTO.getUserId());

		int commentCount = commentFindService.getCount(hashMap)+subCommentFindService.getCount(hashMap);

		model.addAttribute("pageNum",pageNum);
		model.addAttribute("postCount",postCount);
		model.addAttribute("commentCount",commentCount);
		model.addAttribute("score",(2*commentCount+10*postCount));
		model.addAttribute("userId", userDTO.getUserId());

		model.addAttribute("requestPage", PagePath.profilePage);

		return "forward:/";
	}

	@GetMapping("/modify")
	public String modify_get(@SessionAttribute(name = "user") UserDTO userDTO ,Model model) throws Exception {
		model.addAttribute("requestPage", PagePath.modifyPage);

		return "forward:/";
	}

	@PostMapping("/modify")
	public String modify_post(MultipartHttpServletRequest mtfRequest,
											@RequestParam(value = "user_id") String userId,
											 @RequestParam(value = "user_name") String userName,
											 @RequestParam(value = "user_email") String userEmail,
											 @RequestParam(value = "user_birth") String[] userBirthList,
											 @RequestParam(value = "user_phone") String[] userPhoneList,
											 @SessionAttribute("user") UserDTO userDTO,HttpServletResponse response) throws Exception{

		if(userDTO.getUserId().equals(userId)) {
			String userBirth = userBirthList[0] + "-" + userBirthList[1] + "-" + userBirthList[2]+" 00:00:00";
			String userPhone = userPhoneList[0] + "-" + userPhoneList[1] + "-" + userPhoneList[2];

			userDTO.setUserName(userName);
			userDTO.setUserEmail(userEmail);
			userDTO.setUserBirth(Timestamp.valueOf(userBirth));
			userDTO.setUserPhone(userPhone);

			userModifyService.update(userDTO);

			MultipartFile multipartFile = mtfRequest.getFile("upload_file");

			if(multipartFile != null) {
				String fileName = multipartFile.getOriginalFilename();
				String extension = fileName.substring(fileName.lastIndexOf(".")+1);
				fileTools.fileRemover(userId,0, null);

				fileTools.createFile(userId, 0, "profile."+extension,multipartFile);
			}


			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write( "<script>"
														+ "alert('회원정보가 변경되었습니다.');"
														+ "window.location.href='/user/profile';"
														+ "</script>");
			return null;
		}
		else {
			return "redirect:/system/error/auth";
		}
	}
}
