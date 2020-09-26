package com.mkboard.controller;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mkboard.serviceOthers.PostFileService;
import com.mkboard.serviceOthers.PostService;
import com.mkboard.util.CookieTools;
import com.mkboard.util.FileTools;

@Controller
@RequestMapping("/util")
public class UtilController {

	@Autowired
	CookieTools cookieTools;
	@Autowired
	FileTools fileTools;
	@Autowired
	PostFileService postFileService;
	@Autowired
	private PostService postService;

	@GetMapping("/profile")
	public void profile(@RequestParam(name="uid",required = false) String userId,HttpServletResponse response)
			throws Exception {


		File file = fileTools.findFile(userId, 0, null);

		if (file != null) {
			String fileName = URLEncoder.encode(file.getName(), "UTF-8");

			fileName = fileName.replaceAll("\\+", "%20");

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");
			response.setContentLength((int) file.length());


			byte[] fileByte = FileUtils.readFileToByteArray(file);

			response.getOutputStream().write(fileByte);

			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}

	@PostMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, Exception {
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(new InputStreamReader(request.getInputStream(), "UTF-8"));

		String orgFileName = object.get("ofn").toString();
		String storedFileName = object.get("sfn").toString();
		int postId = Integer.parseInt(object.get("pid").toString());
		String userId;

		if(object.get("uid") != null) {
			userId = object.get("uid").toString();
		}else {
			userId = postService.findOne(postId).getUserId();
		}

		File file = fileTools.findFile(userId, postId, storedFileName);

		if (file != null) {

			String fileName = URLEncoder.encode(orgFileName, "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");
			response.setContentLength((int) file.length());


			byte[] fileByte = FileUtils.readFileToByteArray(file);

			response.getOutputStream().write(fileByte);

			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}

	@GetMapping("/cookie")
	public void cookie(@RequestParam(name = "n") String name, @RequestParam(name = "v") String value,
			HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = cookieTools.getCookie(request.getCookies(), name);

		if (cookie != null) {
			cookie.setMaxAge(0);
		}
		cookie = cookieTools.setCookie(name, value, "/", -1);
		response.addCookie(cookie);
	}
}
