package com.mkboard.entity;

import java.sql.Timestamp;

public class CertificationDTO {
	private String userEmail;
	private String key;
	private Timestamp expiry;

	public CertificationDTO() {
		userEmail = null;
		key = null;
		expiry = null;
	}

	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
