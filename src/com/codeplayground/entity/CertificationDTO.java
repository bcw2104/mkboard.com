package com.codeplayground.entity;

import java.sql.Timestamp;

public class CertificationDTO {
	private String userId;
	private String key;
	private Timestamp expiry;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Timestamp getExpiry() {
		return expiry;
	}
	public void setExpiry(Timestamp expiry) {
		this.expiry = expiry;
	}

}
