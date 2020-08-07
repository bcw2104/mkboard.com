package com.codeplayground.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	public ContentController() { }

	@GetMapping("/{categoryId}/{boardId}/{postId}")
	public String postview(@PathVariable String categoryId,
										@PathVariable String boardId,
										@PathVariable String postId,
										HttpSession session,Model model) {

		try {
			int _postId = Integer.parseInt(postId);
			CategoryDTO categoryDTO = categoryService.getCategoryInfo(categoryId);
			BoardDTO boardDTO = boardService.getBoardInfo(boardId);
			PostDTO postDTO = postService.getPostInfo(session, _postId);

			if (categoryDTO != null && boardDTO != null && postDTO != null) {
				ArrayList<CommentDTO> commentList = commentService.getCommentList(_postId, null);
				commentService.addSubCommentList(commentList);
				model.addAttribute("thisPost", postDTO);
				model.addAttribute("thisBoard", boardDTO);
				model.addAttribute("thisCategory", categoryDTO);
				model.addAttribute("closestPostList",
							postService.getClosestPostList(_postId, boardDTO.getBoardId()));
				model.addAttribute("boardList", boardService.getBoardList(categoryDTO.getCategoryId()));
				model.addAttribute("commentList", commentList);
				model.addAttribute("requestPage", "postview.jsp");

				return "forward:/";
			} else {
				return "redirect:/error/access";
			}
		}catch(NumberFormatException e) {
			return "redirect:/error/access";
		}
	}

	@GetMapping({"/", "/{categoryId}","/{categoryId}/{boardId}"})
	public String boardview(@PathVariable(required = false) String categoryId,
											@PathVariable(required = false) String boardId,
											@RequestParam(required = false) String f,
											@RequestParam(required = false) String tar,
											@RequestParam(required = false) String query,
											@RequestParam(required = false) String p, Model model) {

		BoardDTO boardDTO = null;
		CategoryDTO categoryDTO = null;

		categoryDTO = categoryService.getCategoryInfo(categoryId == null ? "community": categoryId);

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
			int pageNum = 0;

			try {
				pageNum = Integer.parseInt(p);
				if (!(pageNum <= (totalCount - 1) / 18 + 1 && pageNum > 0)) {
					pageNum = 1;
				}
			} catch (Exception e) {
				pageNum = 1;
			}

			model.addAttribute("postList", postService.getPostList(boardDTO.getBoardId(),
					categoryDTO.getCategoryId(), f, boardTitle, author, pageNum));
			model.addAttribute("boardList", boardService.getBoardList(categoryDTO.getCategoryId()));
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("pageNum", pageNum);
			model.addAttribute("thisBoard", boardDTO);
			model.addAttribute("thisCategory", categoryDTO);
			model.addAttribute("requestPage", "boardview.jsp");
			return "forward:/";
		} else {
			return "redirect:/error/access";
		}

	}

	@GetMapping("{postId}/comment/sort/{sort}")
	public String comment(@PathVariable String postId,
										 @PathVariable String sort, HttpServletResponse response) {
		int _postId = 0;

		try {
			_postId = Integer.parseInt(postId);
			ArrayList<CommentDTO> commentList = commentService.getCommentList(_postId, sort);
			commentService.addSubCommentList(commentList);

			JSONArray array = commentService.convertToJson(commentList);

			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().write(array.toJSONString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}catch(NumberFormatException e) {
			return "redirect:/error/access";
		}
	}

	@GetMapping({"/{categoryId}/create","/{categoryId}/{boardId}/create"})
	public String postcreate(@PathVariable String categoryId,
											@PathVariable(required = false) String boardId, Model model) {
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
			model.addAttribute("requestPage", "postcreate.jsp");

			return "forward:/";
		} else {
			return "forward:/error/access";
		}
	}

	@PostMapping("/regpost")
	public String regpost(@RequestParam(value = "post_title") String postTitle,
									    @RequestParam(value = "post_content") String postContent,
									    @RequestParam(value = "board_id") String boardId,
									    @SessionAttribute("user") UserDTO userDTO,HttpServletResponse response) {
		if (userDTO != null) {
			String userId = userDTO.getUserId();
			if (!(postTitle.equals("") && postContent.equals("") && boardId.equals(""))) {
				if (postService.uploadPost(postTitle, postContent, boardId, userId)) {
					response.setCharacterEncoding("UTF-8");
					try {
						response.getWriter().write( "<script>"
																	+ "alert('게시글이 업로드되었습니다.');"
																	+ "window.location.href='/';"
																	+ "</script>");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				} else {
					return "redirect:/error/inner";
				}
			} else {
				return "redirect:/error/access";
			}
		} else {
			return "redirect:/error/login";
		}
	}

	@PostMapping("/{postId}/comment/{commentType}")
	public String regcomment(@PathVariable String postId,
											   @PathVariable String commentType,
											   @RequestParam(required = false, value = "comment_content") String commentContent,
											   @RequestParam(value = "parent_id") String parentId,
											   @SessionAttribute("user") UserDTO userDTO,
											   @RequestHeader("Referer") String from) {
		try {
			int _postId = Integer.parseInt(postId);

			if (userDTO != null) {
				String userId = userDTO.getUserId();

				if (commentContent != null && !commentContent.equals("")) {
					if (commentType.equals("regmain")) {
						commentService.addNewComment(_postId, commentContent, userDTO.getUserId());
					}
					else if (commentType.equals("regsub")) {
						int _parentId = Integer.parseInt(parentId);

						_parentId = Integer.parseInt(parentId);
						commentService.addNewSubComment(_postId, _parentId, commentContent, userId);
					}
					return "redirect:"+from;
				} else {
					return "redirect:/error/access";
				}
			} else {
				return "redirect:/error/login";
			}
		} catch (Exception e) {
			return "redirect:/error/access";
		}

	}

}
