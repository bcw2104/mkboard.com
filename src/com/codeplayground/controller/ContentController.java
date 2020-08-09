package com.codeplayground.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.codeplayground.entity.BoardDTO;
import com.codeplayground.entity.CategoryDTO;
import com.codeplayground.entity.CommentDTO;
import com.codeplayground.entity.PostDTO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.service.BoardService;
import com.codeplayground.service.CategoryService;
import com.codeplayground.service.CommentService;
import com.codeplayground.service.PostService;
import com.codeplayground.util.PagePath;
import com.codeplayground.util.Tools;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private Tools tools;

	public ContentController() { }

	@ExceptionHandler(java.lang.NumberFormatException.class)
	public String exception() {
		return "redirect:/system/error/none";
	}

	@GetMapping("/{categoryId}/{boardId}/{postId}")
	public String postview(@PathVariable String categoryId,
										@PathVariable String boardId,
										@PathVariable int postId,
										HttpSession session,Model model) throws Exception{
		CategoryDTO categoryDTO = categoryService.getCategoryInfo(categoryId);
		BoardDTO boardDTO = boardService.getBoardInfo(boardId);
		PostDTO postDTO = postService.getPostInfo(session, postId);

		if (categoryDTO != null && boardDTO != null && postDTO != null) {
			ArrayList<CommentDTO> commentList = commentService.getCommentList(postId, null);
			commentService.addSubCommentList(commentList);
			model.addAttribute("thisPost", postDTO);
			model.addAttribute("thisBoard", boardDTO);
			model.addAttribute("thisCategory", categoryDTO);
			model.addAttribute("closestPostList", postService.getClosestPostList(postId, boardDTO.getBoardId()));
			model.addAttribute("boardList", boardService.getBoardList(categoryDTO.getCategoryId()));
			model.addAttribute("commentList", commentList);
			model.addAttribute("requestPage", PagePath.postviewPage);

			return "forward:/";
		} else {
			return "redirect:/system/error/inner";
		}
	}

	@GetMapping({"/{categoryId}","/{categoryId}/{boardId}"})
	public String boardview(@PathVariable(required = false) String categoryId,
											@PathVariable(required = false) String boardId,
											@RequestParam(required = false) String f,
											@RequestParam(required = false) String tar,
											@RequestParam(required = false) String query,
											@RequestParam(required = false) String p, Model model) throws Exception{

		BoardDTO boardDTO = null;
		CategoryDTO categoryDTO = null;

		categoryDTO = categoryService.getCategoryInfo(categoryId);

		if(boardId == null) {
			boardDTO = new BoardDTO();
			boardDTO.setBoardName("전체 글");
		}else {
			boardDTO = boardService.getBoardInfo(boardId);
		}

		if (boardDTO != null && categoryDTO != null) {

			String boardTitle = "";
			String author = "";

			if (tar != null && query != null) {
				if (tar.equals("title")) {
					boardTitle = query;
				} else if (tar.equals("auth")) {
					author = query;
				}
			}

			int totalCount = postService.getPostCount(boardDTO.getBoardId(), categoryDTO.getCategoryId(),
					boardTitle, author);

			int pageNum = tools.checkPage(p, totalCount, 18);

			model.addAttribute("postList", postService.getPostList(boardDTO.getBoardId(),
					categoryDTO.getCategoryId(), f, boardTitle, author, pageNum));
			model.addAttribute("boardList", boardService.getBoardList(categoryDTO.getCategoryId()));
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("pageNum", pageNum);
			model.addAttribute("thisBoard", boardDTO);
			model.addAttribute("thisCategory", categoryDTO);
			model.addAttribute("requestPage", PagePath.boardviewPage);
			return "forward:/";
		} else {
			return "redirect:/system/error/inner";
		}

	}

	@GetMapping("{postId}/comment/sort/{sort}")
	public String comment(@PathVariable int postId,
										 @PathVariable String sort, HttpServletResponse response) throws Exception{

		ArrayList<CommentDTO> commentList = commentService.getCommentList(postId, sort);
		commentService.addSubCommentList(commentList);

		JSONArray array = commentService.convertToJson(commentList);

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(array.toJSONString());

		return null;
	}

	@GetMapping({"/{categoryId}/create","/{categoryId}/{boardId}/create"})
	public String postcreate(@PathVariable String categoryId,
											@PathVariable(required = false) String boardId, Model model) throws Exception{
		BoardDTO boardDTO = null;
		CategoryDTO categoryDTO = null;

		categoryDTO = categoryService.getCategoryInfo(categoryId);

		if(boardId == null) {
			boardDTO = new BoardDTO();
			boardDTO.setBoardName("전체 글");
		}else {
			boardDTO = boardService.getBoardInfo(boardId);
		}

		if (boardDTO != null && categoryDTO != null) {
			model.addAttribute("boardList", boardService.getBoardList(categoryDTO.getCategoryId()));
			model.addAttribute("thisBoard", boardDTO);
			model.addAttribute("thisCategory", categoryDTO);
			model.addAttribute("requestPage", PagePath.postcreatePage);

			return "forward:/";
		} else {
			return "redirect:/system/error/inner";
		}
	}

	@PostMapping("/regpost")
	public String regpost(@RequestParam(value = "post_title") String postTitle,
									    @RequestParam(value = "post_content") String postContent,
									    @RequestParam(value = "board_id") String boardId,
									    @SessionAttribute("user") UserDTO userDTO,HttpServletResponse response) throws Exception{

		String userId = userDTO.getUserId();
		if (!(postTitle.equals("") && postContent.equals("") && boardId.equals(""))) {
			postService.uploadPost(postTitle, postContent, boardId, userId);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write( "<script>"
														+ "alert('게시글이 업로드되었습니다.');"
														+ "window.location.href='/';"
														+ "</script>");
			return null;
		}else {
			return "redirect:/system/error/inner";
		}
	}

	@PostMapping("/{postId}/regcomment")
	public String regcomment(@PathVariable int postId,
											   @RequestParam(required = false, value = "comment_content") String commentContent,
											   @RequestParam(required = false, value = "parent_id") String parentId,
											   @SessionAttribute("user") UserDTO userDTO,
											   @RequestHeader("Referer") String from) throws Exception{
		String userId = userDTO.getUserId();

		if (commentContent != null && !commentContent.equals("")) {
			if (parentId == null) {
				commentService.addNewComment(postId, commentContent, userId);
			}
			else{
				int _parentId = Integer.parseInt(parentId);

				_parentId = Integer.parseInt(parentId);
				commentService.addNewSubComment(postId, _parentId, commentContent, userId);
			}
			return "redirect:"+from;
		} else {
			return "redirect:/system/error/inner";
		}
	}

}
