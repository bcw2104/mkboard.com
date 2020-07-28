package kr.co.codeplayground.database;

import java.sql.Timestamp;

public class PostDTO {
	private int postId;
	private String postTitle;
	private String postContent;
	private String boardId;
	private String author;
	private Timestamp createDate;
	private int hits;
	
	public PostDTO() {
		postId = 0;
		postTitle = "";
		postContent = "";
		boardId = "";
		author = "";
		createDate = null; 
		hits = 0;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

}
