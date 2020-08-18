package com.codeplayground.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;
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
import com.codeplayground.entity.SubCommentDTO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.service.FindService;
import com.codeplayground.service.ModifyService;
import com.codeplayground.serviceOthers.CommentOtherService;
import com.codeplayground.serviceOthers.PostOtherService;
import com.codeplayground.serviceOthers.SubCommentOtherService;
import com.codeplayground.util.PagePath;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private PostOtherService postOtherService;
	@Autowired
	private CommentOtherService commentOtherService;
	@Autowired
	private SubCommentOtherService subCommentOtherService;

	@Resource(name = "postFindService")
	private FindService<PostDTO> postFindService;

	@Resource(name="postModifyService")
	private ModifyService<PostDTO> postModifyService;

	@Resource(name = "boardFindService")
	private FindService<BoardDTO> boardFindService;

	@Resource(name = "categoryFindService")
	private FindService<CategoryDTO> categoryFindService;

	@Resource(name="commentFindService")
	private FindService<CommentDTO> commentFindService;

	@Resource(name="commentModifyService")
	private ModifyService<CommentDTO> commentModifyService;

	@Resource(name="subCommentFindService")
	private FindService<SubCommentDTO> subCommentFindService;

	@Resource(name="subCommentModifyService")
	private ModifyService<SubCommentDTO> subCommentModifyService;

	public ContentController() { }

	@ExceptionHandler(java.lang.NumberFormatException.class)
	public String exception() {
		return "redirect:/system/error/none";
	}

	@GetMapping("/{categoryId}/{boardId}/{postId}")
	public String postview(@PathVariable String categoryId,
										 @PathVariable String boardId,
										 @PathVariable int postId,
										HttpSession session,Model model) {

		postOtherService.recordVisit(session, postId);

		CategoryDTO categoryDTO = categoryFindService.findOnebyKey(categoryId);
		BoardDTO boardDTO = boardFindService.findOnebyKey(boardId);
		PostDTO postDTO = postFindService.findOnebyKey(postId);

		if (categoryDTO != null && boardDTO != null && postDTO != null) {

			HashMap<String, Object> hashMap = new HashMap<String, Object>();

			hashMap.put("postId", postId);
			hashMap.put("sortType", "DESC");

			ArrayList<CommentDTO> commentList = commentFindService.findList(hashMap);
			subCommentOtherService.attachToParent(commentList);

			model.addAttribute("thisPost", postDTO);

			model.addAttribute("boardId", boardDTO.getBoardId());
			model.addAttribute("boardName", boardDTO.getBoardName());
			model.addAttribute("categoryId", categoryDTO.getCategoryId());
			model.addAttribute("categoryName", categoryDTO.getCategoryName());

			model.addAttribute("closestPostList", postOtherService.getClosestList(postId,boardId));
			model.addAttribute("boardList", boardFindService.findList(categoryId));
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
											@RequestParam(required = false) String query,
											@RequestParam(required = false) String field,
											@RequestParam(required = false,value="sort") String sortType,
											@RequestParam(required = false, value="p") String _pageNum, Model model) throws NumberFormatException{

		BoardDTO boardDTO = null;
		CategoryDTO categoryDTO = categoryFindService.findOnebyKey(categoryId);
		if(boardId == null) {
			boardDTO = new BoardDTO();
			boardDTO.setBoardName("전체 글");
		}else {
			boardDTO = boardFindService.findOnebyKey(boardId);
		}

		if (boardDTO != null && categoryDTO != null) {
			int pageNum = 1;
			String postTitle = "";
			String author = "";

			if (field != null && query != null) {
				if (field.equals("title")) {
					postTitle = query;
				} else if (field.equals("auth")) {
					author = query;
				}
			}
			if(_pageNum != null) {
				pageNum = Integer.parseInt(_pageNum);
			}

			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("important", 1);

			if(!categoryId.equals("notice")){
				hashMap.put("categoryId", "notice");
				//공지사항 가져오기
				model.addAttribute("noticePostList", postFindService.findList(hashMap));
			}

			hashMap.put("categoryId", categoryId);
			hashMap.put("boardId", boardId);
			// 중요한 게시물 가져오기
			model.addAttribute("importantPostList", postFindService.findList(hashMap));

			hashMap.put("postTitle", "%"+postTitle+"%");
			hashMap.put("author", "%"+author+"%");
			hashMap.put("field", postOtherService.sortTypeConverter(sortType));
			hashMap.put("frontPageNum", 18 * (pageNum - 1) + 1);
			hashMap.put("rearPageNum", 18 * pageNum);
			hashMap.put("important", 0);
			//일반 게시물 가져오기
			model.addAttribute("postList", postFindService.findList(hashMap));

			model.addAttribute("boardList", boardFindService.findList(categoryId));
			model.addAttribute("totalCount", postOtherService.getCount(boardId, categoryId, postTitle, author));
			model.addAttribute("pageNum", pageNum);
			model.addAttribute("boardId", boardDTO.getBoardId());
			model.addAttribute("boardName", boardDTO.getBoardName());
			model.addAttribute("categoryId", categoryDTO.getCategoryId());
			model.addAttribute("categoryName", categoryDTO.getCategoryName());
			model.addAttribute("requestPage", PagePath.boardviewPage);

			return "forward:/";
		} else {
			return "redirect:/system/error/inner";
		}

	}

	@GetMapping("{postId}/comment/sort/{sortType}")
	public String comment(@PathVariable int postId,
										 @PathVariable String sortType, HttpServletResponse response) throws Exception{

		if (sortType == null) {
			sortType = "DESC";
		}

		PostDTO postDTO = postFindService.findOnebyKey(postId);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		hashMap.put("postId", postId);
		hashMap.put("sortType", sortType);

		ArrayList<CommentDTO> commentList = commentFindService.findList(hashMap);
		subCommentOtherService.attachToParent(commentList);

		JSONArray result =  commentOtherService.convertToJson(commentList,postDTO.getBoardId());

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result.toJSONString());

		return null;
	}

	@GetMapping({"/{categoryId}/create","/{categoryId}/{boardId}/create"})
	public String postcreate(@PathVariable String categoryId,
											@PathVariable(required = false) String boardId, Model model){

		BoardDTO boardDTO = null;
		CategoryDTO categoryDTO = categoryFindService.findOnebyKey(categoryId);

		if(boardId == null) {
			boardDTO = new BoardDTO();
			boardDTO.setBoardName("전체 글");
		}else {
			boardDTO = boardFindService.findOnebyKey(boardId);
		}

		if (boardDTO != null && categoryDTO != null) {
			model.addAttribute("boardList", boardFindService.findList(categoryId));

			model.addAttribute("boardId", boardDTO.getBoardId());
			model.addAttribute("boardName", boardDTO.getBoardName());
			model.addAttribute("categoryId", categoryDTO.getCategoryId());
			model.addAttribute("categoryName", categoryDTO.getCategoryName());
			model.addAttribute("requestPage", PagePath.postcreatePage);

			return "forward:/";
		} else {
			return "redirect:/system/error/inner";
		}
	}

	@GetMapping({"/{categoryId}/{boardId}/{postId}/modify"})
	public String postmodify(@PathVariable String categoryId,
											@PathVariable String boardId,
											@PathVariable String postId, Model model){

		PostDTO postDTO = postFindService.findOnebyKey(postId);
		BoardDTO boardDTO = boardFindService.findOnebyKey(boardId);
		CategoryDTO categoryDTO = categoryFindService.findOnebyKey(categoryId);

		if (postDTO != null && boardDTO != null && categoryDTO != null) {
			model.addAttribute("boardList", boardFindService.findList(categoryId));

			model.addAttribute("boardId", boardDTO.getBoardId());
			model.addAttribute("boardName", boardDTO.getBoardName());
			model.addAttribute("categoryId", categoryDTO.getCategoryId());
			model.addAttribute("categoryName", categoryDTO.getCategoryName());
			model.addAttribute("thisPost", postDTO);
			model.addAttribute("requestPage", PagePath.postcreatePage);

			return "forward:/";
		} else {
			return "redirect:/system/error/inner";
		}
	}

	@GetMapping("/{postId}/rmvpost")
	public String postremove(@PathVariable String postId,
			 								  @SessionAttribute("user") UserDTO userDTO,HttpServletResponse response) throws Exception{

		PostDTO postDTO = postFindService.findOnebyKey(postId);

		if(postDTO.getAuthor().equals(userDTO.getUserId())) {
			postModifyService.delete(postId);
			String msg = "게시글이 삭제되었습니다.";

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write( "<script>"
														+ "alert('"+msg+"');"
														+ "window.location.href='/';"
														+ "</script>");
			return null;
		}
		else {
			return "redirect:/system/error/auth";
		}
	}

	@PostMapping("/regpost")
	public String regpost(@RequestParam(value = "post_id",required= false) String postId,
										@RequestParam(value = "post_title") String postTitle,
									    @RequestParam(value = "post_content") String postContent,
									    @RequestParam(value = "board_id") String boardId,
									    @RequestParam(value = "important",required = false) String important,
									    @SessionAttribute("user") UserDTO userDTO,HttpServletResponse response) throws Exception{

		String userId = userDTO.getUserId();
		if (!(postTitle.equals("") && postContent.equals("") && boardId.equals(""))) {
			PostDTO postDTO = new PostDTO();
			postDTO.setPostTitle(postTitle);
			postDTO.setPostContent(postContent);
			postDTO.setBoardId(boardId);
			postDTO.setAuthor(userId);
			if(important == null) {
				postDTO.setImportant(0);
			}else {
				postDTO.setImportant(1);
			}

			String msg;
			if(postId == null){
				postModifyService.register(postDTO);
				msg = "게시글이 업로드되었습니다.";
			}
			else{
				postDTO.setPostId(Integer.parseInt(postId));

				postModifyService.update(postDTO);
				msg = "게시글이 변경되었습니다.";
			}

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write( "<script>"
														+ "alert('"+msg+"');"
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
											   @RequestParam(required = false, value = "parent_id") String _parentId,
											   @SessionAttribute("user") UserDTO userDTO,
											   @RequestHeader("Referer") String from) {
		String userId = userDTO.getUserId();

		if (commentContent != null && !commentContent.equals("")) {
			if (_parentId == null) {
				CommentDTO commentDTO = new CommentDTO();
				commentDTO.setPostId(postId);
				commentDTO.setCommentContent(commentContent);
				commentDTO.setUserId(userId);

				commentModifyService.register(commentDTO);
			}
			else{
				int parentId = Integer.parseInt(_parentId);

				SubCommentDTO subCommentDTO = new SubCommentDTO();
				subCommentDTO.setUserId(userId);
				subCommentDTO.setParentId(parentId);
				subCommentDTO.setCommentContent(commentContent);

				commentOtherService.increaseChildCount(parentId);
				subCommentModifyService.register(subCommentDTO);
			}

			postOtherService.increaseComments(postId);

			return "redirect:"+from;
		} else {
			return "redirect:/system/error/inner";
		}
	}

}
