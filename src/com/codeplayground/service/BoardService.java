package com.codeplayground.service;

import java.util.ArrayList;

import com.codeplayground.database.BoardDAO;
import com.codeplayground.database.BoardDTO;

public class BoardService {

	public BoardDTO getBoardInfo(String boardId) {
		BoardDAO boardDAO = new BoardDAO();

		return boardDAO.getBoardInfo(boardId);
	}

	public ArrayList<BoardDTO> getBoardList(String categoryId) {
		BoardDAO boardDAO = new BoardDAO();

		return boardDAO.getBoardList(categoryId);
	}

}
