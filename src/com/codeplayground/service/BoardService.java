package com.codeplayground.service;

import java.util.ArrayList;

import com.codeplayground.dao.BoardDAO;
import com.codeplayground.entity.BoardDTO;

public class BoardService {

	public BoardDTO getBoardInfo(String boardId) {
		BoardDAO boardDAO = new BoardDAO();

		return boardDAO.getBoard(boardId);
	}

	public ArrayList<BoardDTO> getBoardList(String categoryId) {
		BoardDAO boardDAO = new BoardDAO();

		return boardDAO.getBoardList(categoryId);
	}

}
