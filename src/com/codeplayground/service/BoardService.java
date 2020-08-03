package com.codeplayground.service;

import java.util.ArrayList;

import com.codeplayground.dao.BoardDAO;
import com.codeplayground.entity.BoardDTO;

public class BoardService {
	private BoardDAO boardDAO;

	public BoardService() {
		boardDAO = new BoardDAO();
	}

	public BoardDTO getBoardInfo(String boardId) {
		return boardDAO.getBoard(boardId);
	}

	public ArrayList<BoardDTO> getBoardList(String categoryId) {
		return boardDAO.getBoardList(categoryId);
	}

}
