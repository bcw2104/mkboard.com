package com.codeplayground.entity;

import java.sql.Timestamp;

public class SubCommentDTO {
	private int commentId;
	private String commentContent;
	private String userId;
	private Timestamp createDate;
	private int parentId;

	public SubCommentDTO() {
		commentId = 0;
		commentContent =null;
		userId = null;
		createDate = null;
		parentId = 0;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}


}
