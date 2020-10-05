package com.mkboard.controller;

import java.util.ArrayList;
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

import com.mkboard.entity.BoardDTO;
import com.mkboard.entity.CategoryDTO;
import com.mkboard.entity.CommentDTO;
import com.mkboard.entity.PostDTO;
import com.mkboard.entity.PostFileDTO;
import com.mkboard.entity.PostInfoDTO;
import com.mkboard.entity.SubCommentDTO;
import com.mkboard.entity.UserDTO;
import com.mkboard.service.ModifyService;
import com.mkboard.serviceOthers.BoardService;
import com.mkboard.serviceOthers.CategoryService;
import com.mkboard.serviceOthers.CommentService;
import com.mkboard.serviceOthers.PostFileService;
import com.mkboard.serviceOthers.PostService;
import com.mkboard.serviceOthers.SequenceService;
import com.mkboard.util.PagePath;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private PostService postService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private PostFileService postFileService;

	@Resource(name="postModifyService")
	private ModifyService<PostDTO> postModifyService;

	@Resource(name="commentModifyService")
	private ModifyService<CommentDTO> commentModifyService;

	@Resource(name="subCommentModifyService")
	private ModifyService<SubCommentDTO> subCommentModifyService;

	public ContentController() { }

	@GetMapping("/{categoryId}/{boardId}/{postId}")
	public String postview(@PathVariable String categoryId,
										 @PathVariable String boardId,
										 @PathVariable int postId,
										HttpSession session,Model model) {

		postService.recordVisit(session, postId);

		CategoryDTO categoryDTO = categoryService.findOne(categoryId);
		BoardDTO boardDTO = boardService.findOne(boardId);
		PostInfoDTO postInfoDTO = postService.findOne(postId);

		if (postInfoDTO.getPermission() == 1 && categoryDTO != null && boardDTO != null && postInfoDTO != null) {

			ArrayList<CommentDTO> commentList = commentService.findList(postId, null , "DESC");
			Iterator<CommentDTO> itr = commentList.iterator();
			CommentDTO temp = null;

			while (itr.hasNext()) {
				temp = itr.next();
				if (temp.getChildCount() > 0) {
					temp.setSubComment(commentService.findSubList(temp.getCommentId(),null));
				}
			}


			model.addAttribute("postInfo", postInfoDTO);
			model.addAttribute("categoryId", categoryDTO.getCategoryId());
			model.addAttribute("categoryName", categoryDTO.getCategoryName());

			model.addAttribute("closestPostList", postService.getClosestList(categoryId,boardId,postId));
			model.addAttribute("boardList", boardService.findList(categoryId));
			model.addAttribute("commentList", commentList);
			model.addAttribute("requestPage", PagePath.postviewPage);

			//files
			ArrayList<PostFileDTO> fileList = postFileService.findList(postInfoDTO.getUserId(), postId,"attach");

			if(!fileList.isEmpty()) {
				model.addAttribute("attachedFileList", fileList);
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


			if(!categoryId.equals("notice")){
				//공지사항 가져오기
				model.addAttribute("noticePostList", postService.findList("notice",null,1));
			}

			// 중요한 게시물 가져오기
			model.addAttribute("importantPostList", postService.findList(categoryId,boardId,1));


			String postTitle = null;
			String userNickName = null;

			if (field != null && query != null) {
				if (field.equals("title")) {
					postTitle = query;
				} else if (field.equals("nick")) {
					userNickName = query;
				}
			}

			if(_pageNum != null) {
				pageNum = Integer.parseInt(_pageNum);
			}

			//일반 게시물 가져오기
			model.addAttribute("postList", postService.findList(categoryId,boardId,0,postTitle,userNickName,false,sortType
																				,18 * (pageNum - 1) + 1,18 * pageNum));


			model.addAttribute("boardList", boardService.findList(categoryId));
			model.addAttribute("postCount", postService.getCount(categoryId,boardId,0,postTitle,userNickName,false));
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

		PostInfoDTO postInfoDTO = postService.findOne(postId);

		ArrayList<CommentDTO> commentList = commentService.findList(postId,null,sortType);
		Iterator<CommentDTO> itr = commentList.iterator();
		CommentDTO temp = null;

		while (itr.hasNext()) {
			temp = itr.next();
			if (temp.getChildCount() > 0) {
				temp.setSubComment(commentService.findSubList(temp.getCommentId(),null));
			}
		}

		JSONArray result = commentService.convertToJson(commentList,postInfoDTO.getBoardId());

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result.toJSONString());

		return null;
	}

	@GetMapping({"/{categoryId}/create","/{categoryId}/{boardId}/create"})
	public String create(@PathVariable String categoryId,
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
	public String modify(@PathVariable String categoryId,
											@PathVariable String boardId,
											@PathVariable int postId,
											 @SessionAttribute("user") UserDTO userDTO, Model model) throws Exception{

		PostInfoDTO postInfoDTO = postService.findOne(postId);

		if(postInfoDTO.getUserId().equals(userDTO.getUserId())) {
			BoardDTO boardDTO = boardService.findOne(boardId);
			CategoryDTO categoryDTO = categoryService.findOne(categoryId);

			if (postInfoDTO != null && boardDTO != null && categoryDTO != null) {

				model.addAttribute("attachedFileList", postFileService.findList(userDTO.getUserId(), postId,"attach"));
				model.addAttribute("boardList", boardService.findList(categoryId));
				model.addAttribute("categoryId", categoryDTO.getCategoryId());
				model.addAttribute("categoryName", categoryDTO.getCategoryName());
				model.addAttribute("postInfo", postInfoDTO);
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
	public String remove(@PathVariable int postId,
			 								  @SessionAttribute("user") UserDTO userDTO,HttpServletResponse response) throws Exception{

		PostDTO postDTO = postService.findOne(postId);

		if(postDTO.getUserId().equals(userDTO.getUserId())) {
			String msg = "게시글이 삭제되었습니다.";

			postFileService.delete(postDTO.getUserId(),postDTO.getPostId(), null);
			postModifyService.delete(postId);

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

	@GetMapping("/{postId}/blind")
	public String blind(@PathVariable int postId,
			 								  @SessionAttribute("user") UserDTO userDTO,HttpServletResponse response) throws Exception{

		PostDTO postDTO = postService.findOne(postId);

		if(userDTO.getAdmin() == 1) {
			String msg = "게시글이 블라인드 처리되었습니다.";
			postDTO.setPermission(0);
			postModifyService.update(postDTO);

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
									    @RequestParam(value = "org_attached_file",required= false) String[] orgAttachedFiles,
									    MultipartHttpServletRequest mtfRequest,
									    @SessionAttribute("user") UserDTO userDTO,HttpServletResponse response) throws Exception{

		String userId = userDTO.getUserId();

		if (!(postTitle.equals("") && postContent.equals("") && boardId.equals(""))) {
			PostDTO postDTO = new PostDTO();
			int postId;
			boolean regState = false;
			String msg = "";

			List<MultipartFile> fileList = mtfRequest.getFiles("attached_file");

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

				if(postService.findOne(postId).getUserId().equals(userId)) {
					postModifyService.update(postDTO);

					ArrayList<PostFileDTO> postAttachedFiles = postFileService.findList(userId, postId,"attach");

					if(orgAttachedFiles != null) {
						for(int i=0; i<orgAttachedFiles.length; i++) {
							for(int j=0; j<postAttachedFiles.size(); j++) {
								if(orgAttachedFiles[i].equals(postAttachedFiles.get(j).getStoredFileName())) {
									postAttachedFiles.remove(j);
									break;
								}
							}
						}

						for(int i=0; i<postAttachedFiles.size(); i++) {
							postFileService.delete(userId, postId, postAttachedFiles.get(i).getStoredFileName());
						}

					}else {
						postFileService.delete(userId, postId, null);
					}

					msg = "게시글이 변경되었습니다.";
					regState = true;
				}
			}

			if(regState) {
				postFileService.register(userId,postId, fileList,"attach");

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

				commentService.increaseChildCount(parentId);
				subCommentModifyService.register(subCommentDTO);
			}


			postService.increase(postId, 0, 1);

			return "redirect:"+from;
		} else {
			return "redirect:/system/error/inner";
		}
	}

}
