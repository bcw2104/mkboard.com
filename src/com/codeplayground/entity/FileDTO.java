package com.codeplayground.entity;

import java.sql.Timestamp;

public class FileDTO {
	private int fileNo;
	private int postId;
	private String orgFileName;
	private String storedFileName;
	private long fileSize;
	private Timestamp createDate;
	private String userId;

	public FileDTO() {
		this.fileNo = 0;
		this.postId = 0;
		this.orgFileName = null;
		this.storedFileName = null;
		this.fileSize = 0;
		this.createDate = null;
		this.userId = null;
	}

	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileId) {
		this.fileNo = fileId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getOrgFileName() {
		return orgFileName;
	}
	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}
	public String getStoredFileName() {
		return storedFileName;
	}
	public void setStoredFileName(String storedFileName) {
		this.storedFileName = storedFileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}


}
