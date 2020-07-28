package com.codeplayground.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codeplayground.dao.PostDAO;
import com.codeplayground.entity.BoardDTO;
import com.codeplayground.entity.CategoryDTO;
import com.codeplayground.entity.PostDTO;
import com.codeplayground.service.BoardService;
import com.codeplayground.service.CategoryService;
import com.codeplayground.service.PostService;

@WebServlet("/content/*")
public class ContentController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionPostDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionPostDo(request, response);
	}

	private void actionPostDo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// path[0] = categoryId, path[1] = boardId, path[3] = postId
		String[] params = request.getRequestURI().substring(9).split("/");


		if (params.length <= 2) {
			PostService postService = new PostService();
			BoardService boardService = new BoardService();
			CategoryService categoryService = new CategoryService();

			BoardDTO boardDTO = null;
			CategoryDTO categoryDTO = null;

			categoryDTO = categoryService.getCategoryInfo(params[0]);
			if (params.length == 2) {
				boardDTO = boardService.getBoardInfo(params[1]);
			} else {
				boardDTO = new BoardDTO();
				boardDTO.setBoardName("전체 글");
			}

			String field = request.getParameter("f");
			String target = request.getParameter("tar");
			String query = request.getParameter("query");

			String boardTitle = "";
			String author = "";

			if (field == null) {
				field = "";
			}

			if (target != null && query != null) {
				if (target.equals("title")) {
					boardTitle = query;
				} else if (target.equals("auth")) {
					author = query;
				}
			}

			int totalCount = postService.getTotalPostCount(boardDTO.getBoardId(), categoryDTO.getCategoryId(), boardTitle, author);
			int pageNum = 0;

			try {
				pageNum = Integer.parseInt(request.getParameter("p"));
				if (!(pageNum <= (totalCount - 1) / 18 + 1 && pageNum > 0)) {
					pageNum = 1;
				}
			} catch (Exception e) {
				pageNum = 1;
			}
			request.setAttribute("postList", postService.getPostList(boardDTO.getBoardId(), categoryDTO.getCategoryId(),
					field, boardTitle, author, pageNum));
			request.setAttribute("boardList", boardService.getBoardList(categoryDTO.getCategoryId()));
			request.setAttribute("totalCount", totalCount);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("thisBoard", boardDTO);
			request.setAttribute("thisCategory", categoryDTO);
			request.setAttribute("requestPage", "content.jsp");
			request.getRequestDispatcher("/").forward(request, response);
		} else if (params.length == 3) {
			PostService postService = new PostService();

			PostDTO postDTO = postService.clickPost(params[2]);

			System.out.println(postDTO.getPostTitle());
		}

	}

}
