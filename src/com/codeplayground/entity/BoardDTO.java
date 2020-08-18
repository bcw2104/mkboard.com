package com.codeplayground.entity;

public class BoardDTO {
	private String boardId;
	private String boardName;
	private String categoryId;

	public BoardDTO() {
		boardId = null;
		boardName = null;
		categoryId = null;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

}
