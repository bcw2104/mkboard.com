package kr.co.codeplayground.service;

import java.util.ArrayList;

import kr.co.codeplayground.database.BoardDAO;
import kr.co.codeplayground.database.BoardDTO;
import kr.co.codeplayground.database.PostDAO;
import kr.co.codeplayground.database.PostDTO;

public class BoardService {

	public String fieldConverter(String field) {
		String result = "post_id";

		if (field.equals("id")) {
			result = "post_id";
		}
		return result;
	}

	public BoardDTO getBoardInfo(String boardId) {
		BoardDAO boardDAO = new BoardDAO();

		return boardDAO.getBoardInfo(boardId);
	}

	public ArrayList<BoardDTO> getBoardList(String categoryId) {
		BoardDAO boardDAO = new BoardDAO();

		return boardDAO.getBoardList(categoryId);
	}

	public int getTotalPostCount(BoardDTO boardDTO, String postTitle, String author) {
		PostDAO dao = new PostDAO();

		return dao.getTotalPostCount(boardDTO, postTitle, author);
	}

	public ArrayList<PostDTO> getPostList(String field, BoardDTO boardDTO, String postTitle, String author, int page) {
		PostDAO postDAO = new PostDAO();

		return postDAO.getPostList(fieldConverter(field), boardDTO, postTitle, author, page);
	}

}
