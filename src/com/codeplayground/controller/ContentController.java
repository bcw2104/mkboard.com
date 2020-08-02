package com.codeplayground.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.codeplayground.entity.BoardDTO;
import com.codeplayground.entity.CategoryDTO;
import com.codeplayground.entity.CommentDTO;
import com.codeplayground.entity.PostDTO;
import com.codeplayground.entity.UserDTO;
import com.codeplayground.service.BoardService;
import com.codeplayground.service.CategoryService;
import com.codeplayground.service.CommentService;
import com.codeplayground.service.PostService;

@WebServlet("/content/*")
public class ContentController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// path[0] = categoryId, path[1] = boardId, path[3] = postId
		String[] params = request.getRequestURI().substring(9).split("/");

		BoardService boardService = new BoardService();
		CategoryService categoryService = new CategoryService();
		PostService postService = new PostService();

		if (params[params.length - 1].equals("create")) {
			BoardDTO boardDTO = null;
			CategoryDTO categoryDTO = null;

			categoryDTO = categoryService.getCategoryInfo(params[0]);
			if (params.length == 3) {
				boardDTO = boardService.getBoardInfo(params[1]);
			} else {
				boardDTO = new BoardDTO();
				boardDTO.setBoardName("전체 글");
			}
			if (boardDTO != null && categoryDTO != null) {
				request.setAttribute("boardList", boardService.getBoardList(categoryDTO.getCategoryId()));
				request.setAttribute("thisBoard", boardDTO);
				request.setAttribute("thisCategory", categoryDTO);
				request.setAttribute("requestPage", "postcreate.jsp");
				request.getRequestDispatcher("/").forward(request, response);
			}
			else {
				response.sendRedirect("/error/access");
			}
		} else {
			if (params.length <= 2) {
				BoardDTO boardDTO = null;
				CategoryDTO categoryDTO = null;

				categoryDTO = categoryService.getCategoryInfo(params[0]);
				if (params.length == 2) {
					boardDTO = boardService.getBoardInfo(params[1]);
				} else {
					boardDTO = new BoardDTO();
					boardDTO.setBoardName("전체 글");
				}

				if (boardDTO != null && categoryDTO != null) {
					String field = request.getParameter("f");
					String target = request.getParameter("tar");
					String query = request.getParameter("query");

					String boardTitle = "";
					String author = "";

					if (target != null && query != null) {
						if (target.equals("title")) {
							boardTitle = query;
						} else if (target.equals("auth")) {
							author = query;
						}
					}

					int totalCount = postService.getPostCount(boardDTO.getBoardId(), categoryDTO.getCategoryId(),
							boardTitle, author);
					int pageNum = 0;

					try {
						pageNum = Integer.parseInt(request.getParameter("p"));
						if (!(pageNum <= (totalCount - 1) / 18 + 1 && pageNum > 0)) {
							pageNum = 1;
						}
					} catch (Exception e) {
						pageNum = 1;
					}

					request.setAttribute("postList", postService.getPostList(boardDTO.getBoardId(),
							categoryDTO.getCategoryId(), field, boardTitle, author, pageNum));
					request.setAttribute("boardList", boardService.getBoardList(categoryDTO.getCategoryId()));
					request.setAttribute("totalCount", totalCount);
					request.setAttribute("pageNum", pageNum);
					request.setAttribute("thisBoard", boardDTO);
					request.setAttribute("thisCategory", categoryDTO);
					request.setAttribute("requestPage", "boardview.jsp");
					request.getRequestDispatcher("/").forward(request, response);
				} else {
					response.sendRedirect("/error/access");
				}
			} else if (params.length == 3) {
				String cmd = "";

				if (request.getParameter("cmd") != null) {
					cmd = request.getParameter("cmd");
				}

				int postId = 0;

				try {
					postId = Integer.parseInt(params[2]);

					if (cmd.equals("sort")) {
						String sort = "DESC";
						if (request.getParameter("sort") != null) {
							sort = request.getParameter("sort");
						}

						CommentService commentService = new CommentService();
						ArrayList<CommentDTO> commentList = commentService.getCommentList(postId, sort);
						commentService.addSubCommentList(commentList);

						JSONArray array = commentService.convertToJson(commentList);

						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(array.toJSONString());
					} else {

						CommentService commentService = new CommentService();
						CategoryDTO categoryDTO = categoryService.getCategoryInfo(params[0]);
						BoardDTO boardDTO = boardService.getBoardInfo(params[1]);
						PostDTO postDTO = postService.getPostInfo(request.getSession(), postId);

						if (categoryDTO != null && boardDTO != null && postDTO != null) {
							ArrayList<CommentDTO> commentList = commentService.getCommentList(postId, null);
							commentService.addSubCommentList(commentList);

							request.setAttribute("thisPost", postDTO);
							request.setAttribute("thisBoard", boardDTO);
							request.setAttribute("thisCategory", categoryDTO);
							request.setAttribute("closestPostList",
									postService.getClosestPostList(postId, boardDTO.getBoardId()));
							request.setAttribute("boardList", boardService.getBoardList(categoryDTO.getCategoryId()));
							request.setAttribute("commentList", commentList);
							request.setAttribute("requestPage", "postview.jsp");
							request.getRequestDispatcher("/").forward(request, response);

						} else {
							response.sendRedirect("/error/access");
						}
					}
				} catch (Exception e) {
					response.sendRedirect("/error/access");
				}
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = "";
		if (request.getParameter("cmd") != null) {
			cmd = request.getParameter("cmd");
		}
		// path[0] = categoryId, path[1] = boardId, path[3] = postId
		String[] params = request.getRequestURI().substring(9).split("/");

		if (params[params.length - 1].equals("regpost")) {
			PostService postService = new PostService();
			if (request.getSession().getAttribute("user") != null) {
				UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");
				String postTitle = request.getParameter("post_title");
				String postContent = request.getParameter("post_content");
				String boardId = request.getParameter("board_id");
				if(!(postTitle.equals("") && postContent.equals("") && boardId.equals(""))) {
					if(postService.uploadPost(postTitle, postContent, boardId, userDTO.getUserId())) {
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write("<script>"
																+ "alert('게시글이 업로드되었습니다.');"
																+ "window.location.href='/';"
																+ "</script>");
					}else {
						response.sendRedirect("/error/inner");
					}
				}else {
					response.sendRedirect("/error/access");
				}
			} else {
				response.sendRedirect("/error/login");
			}
		} else {
			try {
				int postId = 0;
				CommentService commentService = new CommentService();
				postId = Integer.parseInt(params[2]);

				if (request.getSession().getAttribute("user") != null) {
					UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");

					String content = request.getParameter("comment_content");
					if (content != null && !content.equals("")) {
						if (cmd.equals("main")) {
							commentService.addNewComment(postId, request.getParameter("comment_content"),
									userDTO.getUserId());
						} else if (cmd.equals("sub")) {
							int parentId = 0;
							parentId = Integer.parseInt(request.getParameter("parent_id"));
							commentService.addNewSubComment(postId, parentId, request.getParameter("comment_content"),
									userDTO.getUserId());
						}
						response.sendRedirect(request.getHeader("Referer"));
					} else {
						response.sendRedirect("/error/access");
					}
				} else {
					response.sendRedirect("/error/login");
				}
			} catch (Exception e) {
				response.sendRedirect("/error/login");
			}
		}
	}

}
