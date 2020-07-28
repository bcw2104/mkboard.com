package com.codeplayground.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codeplayground.database.BoardDTO;
import com.codeplayground.service.BoardService;

@WebServlet("/content/*")
public class BoardController extends HttpServlet {

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

		BoardService service = new BoardService();
		String cmd = request.getParameter("cmd");
		String[] path = request.getRequestURI().substring(9).split("/");

		BoardDTO boardDTO;

		//path[0] = categoryId, path[1] = boardId
		if(path.length == 1) {
			boardDTO = new BoardDTO();
			boardDTO.setCategoryId(path[0]);
		}
		else if(path.length == 2) {
			boardDTO = service.getBoardInfo(path[1]);
		}
		else {
			boardDTO = new BoardDTO();
		}

		//page command
		if (cmd == null || cmd.equals("")) {
			cmd = "sl";			//default = select
		}

		if (cmd.equals("sl")) {
			String field = request.getParameter("f");
			String target = request.getParameter("tar");
			String query = request.getParameter("query");

			String boardTitle = "";
			String author = "";

			if (field == null) {
				field = "";
			}

			if (target != null && query != null) {
				if(target.equals("title")) {
					boardTitle = query;
				}
				else if(target.equals("auth")) {
					author = query;
				}
			}

			int totalCount = service.getTotalPostCount(boardDTO, boardTitle, author);
			int pageNum = 0;

			try {
				pageNum = Integer.parseInt(request.getParameter("p"));
				if (!(pageNum <= (totalCount - 1) / 18 + 1 && pageNum > 0)) {
					pageNum = 1;
				}
			} catch (Exception e) {
				pageNum = 1;
			}
			request.setAttribute("postList",service.getPostList(field, boardDTO, boardTitle, author, pageNum));
			request.setAttribute("boardList", service.getBoardList(boardDTO.getCategoryId()));
			request.setAttribute("totalCount", totalCount);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("currentBoard", boardDTO);
			request.setAttribute("requestPage", "content.jsp");
			request.getRequestDispatcher("/").forward(request, response);
		}

	}

}
