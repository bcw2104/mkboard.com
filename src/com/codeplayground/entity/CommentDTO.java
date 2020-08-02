package com.codeplayground.entity;

import java.sql.Timestamp;
import java.util.ArrayList;

public class CommentDTO {
	private int commentId;
	private int postId;
	private String commentContent;
	private String userId;
	private Timestamp createDate;
	private int childCount;
	private ArrayList<SubCommentDTO> subComment;

	public CommentDTO() {
		commentId = 0;
		postId = 0;
		commentContent = "";
		userId = "";
		createDate = null;
		childCount = 0;
		subComment = new ArrayList<SubCommentDTO>();
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
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

	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	public ArrayList<SubCommentDTO> getSubComment() {
		return subComment;
	}

	public void setSubComment(ArrayList<SubCommentDTO> subComment) {
		this.subComment = subComment;
	}



}
