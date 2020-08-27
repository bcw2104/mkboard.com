package com.codeplayground.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.codeplayground.entity.BoardDTO;
import com.codeplayground.entity.CategoryDTO;
import com.codeplayground.entity.CommentDTO;
import com.codeplayground.entity.FileDTO;
import com.codeplayground.entity.PostDTO;
import com.codeplayground.entity.SubCommentDTO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.service.FindService;
import com.codeplayground.service.ModifyService;
import com.codeplayground.serviceOthers.BoardService;
import com.codeplayground.serviceOthers.CategoryService;
import com.codeplayground.serviceOthers.CommentOtherService;
import com.codeplayground.serviceOthers.PostFileService;
import com.codeplayground.serviceOthers.PostOtherService;
import com.codeplayground.serviceOthers.SequenceService;
import com.codeplayground.util.PagePath;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private PostOtherService postOtherService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommentOtherService commentOtherService;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private PostFileService fileService;

	@Resource(name = "postFindService")
	private FindService<PostDTO> postFindService;

	@Resource(name="postModifyService")
	private ModifyService<PostDTO> postModifyService;

	@Resource(name="commentFindService")
	private FindService<CommentDTO> commentFindService;

	@Resource(name="commentModifyService")
	private ModifyService<CommentDTO> commentModifyService;

	@Resource(name="subCommentFindService")
	private FindService<SubCommentDTO> subCommentFindService;

	@Resource(name="subCommentModifyService")
	private ModifyService<SubCommentDTO> subCommentModifyService;

	public ContentController() { }

	@GetMapping("/{categoryId}/{boardId}/{postId}")
	public String postview(@PathVariable String categoryId,
										 @PathVariable String boardId,
										 @PathVariable int postId,
										HttpSession session,Model model) {

		postOtherService.recordVisit(session, postId);

		CategoryDTO categoryDTO = categoryService.findOne(categoryId);
		BoardDTO boardDTO = boardService.findOne(boardId);
		PostDTO postDTO = postFindService.findOne(postId);

		if (categoryDTO != null && boardDTO != null && postDTO != null) {

			HashMap<String, Object> hashMap = new HashMap<String, Object>();

			hashMap.put("postId", postId);
			hashMap.put("sortType", "DESC");

			ArrayList<CommentDTO> commentList = commentFindService.findList(hashMap);
			Iterator<CommentDTO> itr = commentList.iterator();
			CommentDTO temp = null;

			while (itr.hasNext()) {
				temp = itr.next();
				if (temp.getChildCount() > 0) {
					HashMap<String, Object> subHashMap = new HashMap<String, Object>();
					subHashMap.put("parentId", temp.getCommentId());
					temp.setSubComment(subCommentFindService.findList(hashMap));
				}
			}


			model.addAttribute("thisPost", postDTO);

			model.addAttribute("boardId", boardDTO.getBoardId());
			model.addAttribute("boardName", boardDTO.getBoardName());
			model.addAttribute("categoryId", categoryDTO.getCategoryId());
			model.addAttribute("categoryName", categoryDTO.getCategoryName());

			model.addAttribute("closestPostList", postOtherService.getClosestList(postId,boardId));
			model.addAttribute("boardList", boardService.findList(categoryId));
			model.addAttribute("commentList", commentList);
			model.addAttribute("requestPage", PagePath.postviewPage);

			//files
			ArrayList<FileDTO> fileList = fileService.findList(postDTO.getUserId(), postId);

			if(!fileList.isEmpty()) {
				model.addAttribute("uploadFileList", fileList);
			}

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
		CategoryDTO categoryDTO = categoryService.findOne(categoryId);
		if(boardId == null) {
			boardDTO = new BoardDTO();
			boardDTO.setBoardName("전체 글");
		}else {
			boardDTO = boardService.findOne(boardId);
		}

		if (boardDTO != null && categoryDTO != null) {
			int pageNum = 1;

			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("important", 1);

			if(!categoryId.equals("notice")){
				hashMap.put("categoryId", "notice");
				//공지사항 가져오기
				model.addAttribute("noticePostList", postOtherService.findListjoinBoard(hashMap));
			}

			hashMap.put("categoryId", categoryId);
			hashMap.put("boardId", boardId);
			// 중요한 게시물 가져오기

			if(boardId == null) {
				model.addAttribute("importantPostList", postOtherService.findListjoinBoard(hashMap));
			}
			else {
				model.addAttribute("importantPostList", postFindService.findList(hashMap));
			}

			if (field != null && query != null) {
				if (field.equals("title")) {
					hashMap.put("postTitle", query);
				} else if (field.equals("auth")) {
					hashMap.put("userId", query);
				}
			}

			if(_pageNum != null) {
				pageNum = Integer.parseInt(_pageNum);
			}

			hashMap.put("sortType", postOtherService.sortTypeConverter(sortType));
			hashMap.put("frontPageNum", 18 * (pageNum - 1) + 1);
			hashMap.put("rearPageNum", 18 * pageNum);
			hashMap.put("important", 0);
			//일반 게시물 가져오기

			if(boardId == null) {
				model.addAttribute("postList", postOtherService.findListjoinBoard(hashMap));
			}
			else {
				model.addAttribute("postList", postFindService.findList(hashMap));
			}

			model.addAttribute("boardList", boardService.findList(categoryId));
			model.addAttribute("postCount", postFindService.getCount(hashMap));
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

		PostDTO postDTO = postFindService.findOne(postId);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		hashMap.put("postId", postId);
		hashMap.put("sortType", sortType);

		ArrayList<CommentDTO> commentList = commentFindService.findList(hashMap);
		Iterator<CommentDTO> itr = commentList.iterator();
		CommentDTO temp = null;

		while (itr.hasNext()) {
			temp = itr.next();
			if (temp.getChildCount() > 0) {
				HashMap<String, Object> subHashMap = new HashMap<String, Object>();
				subHashMap.put("parentId", temp.getCommentId());
				temp.setSubComment(subCommentFindService.findList(hashMap));
			}
		}

		JSONArray result =  commentOtherService.convertToJson(commentList,postDTO.getBoardId());

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result.toJSONString());

		return null;
	}

	@GetMapping({"/{categoryId}/create","/{categoryId}/{boardId}/create"})
	public String postcreate(@PathVariable String categoryId,
											@PathVariable(required = false) String boardId, Model model){

		BoardDTO boardDTO = null;
		CategoryDTO categoryDTO = categoryService.findOne(categoryId);

		if(boardId == null) {
			boardDTO = new BoardDTO();
			boardDTO.setBoardName("전체 글");
		}else {
			boardDTO = boardService.findOne(boardId);
		}

		if (boardDTO != null && categoryDTO != null) {
			model.addAttribute("boardList", boardService.findList(categoryId));

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
											@PathVariable String postId,
											 @SessionAttribute("user") UserDTO userDTO, Model model){

		PostDTO postDTO = postFindService.findOne(postId);

		if(postDTO.getUserId().equals(userDTO.getUserId())) {
			BoardDTO boardDTO = boardService.findOne(boardId);
			CategoryDTO categoryDTO = categoryService.findOne(categoryId);

			if (postDTO != null && boardDTO != null && categoryDTO != null) {
				model.addAttribute("boardList", boardService.findList(categoryId));

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
		else {
			return "redirect:/system/error/auth";
		}

	}

	@GetMapping("/{postId}/rmvpost")
	public String postremove(@PathVariable String postId,
			 								  @SessionAttribute("user") UserDTO userDTO,HttpServletResponse response) throws Exception{

		PostDTO postDTO = postFindService.findOne(postId);

		if(postDTO.getUserId().equals(userDTO.getUserId())) {
			fileService.delete(postDTO.getUserId(),postDTO.getPostId(), null);
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
	public String regpost(@RequestParam(value = "post_id",required= false) String _postId,
										@RequestParam(value = "post_title") String postTitle,
									    @RequestParam(value = "post_content") String postContent,
									    @RequestParam(value = "board_id") String boardId,
									    @RequestParam(value = "important",required = false) String important,
									    MultipartHttpServletRequest mtfRequest,
									    @SessionAttribute("user") UserDTO userDTO,HttpServletResponse response) throws Exception{

		String userId = userDTO.getUserId();

		if (!(postTitle.equals("") && postContent.equals("") && boardId.equals(""))) {
			PostDTO postDTO = new PostDTO();
			int postId;
			boolean regState = false;
			String msg = "";

			List<MultipartFile> fileList = mtfRequest.getFiles("upload_file");


			if(important == null) {
				postDTO.setImportant(0);
			}else {
				postDTO.setImportant(1);
			}

			postDTO.setPostTitle(postTitle);
			postDTO.setPostContent(postContent);
			postDTO.setBoardId(boardId);
			postDTO.setUserId(userId);

			if(_postId == null){
				postId = sequenceService.getNextSequence("post");
				postDTO.setPostId(postId);
				postModifyService.register(postDTO);
				msg = "게시글이 업로드되었습니다.";
				regState = true;

			}
			else {
				postId = Integer.parseInt(_postId);
				postDTO.setPostId(postId);

				fileService.delete(userId, postId, null);

				if(postFindService.findOne(_postId).getUserId().equals(userId)) {
					postModifyService.update(postDTO);
					msg = "게시글이 변경되었습니다.";
					regState = true;
				}
			}

			if(regState) {
				fileService.register(userId,postId, fileList);

				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().write( "<script>"
															+ "alert('"+msg+"');"
															+ "window.location.href='/';"
															+ "</script>");
				return null;

			}else {
				return "redirect:/system/error/auth";
			}

		}
		else {
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
				commentDTO.setCommentId(sequenceService.getNextSequence("comment"));

				commentModifyService.register(commentDTO);
			}
			else{
				int parentId = Integer.parseInt(_parentId);

				SubCommentDTO subCommentDTO = new SubCommentDTO();
				subCommentDTO.setUserId(userId);
				subCommentDTO.setParentId(parentId);
				subCommentDTO.setCommentContent(commentContent);
				subCommentDTO.setCommentId(sequenceService.getNextSequence("subcomment"));

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
