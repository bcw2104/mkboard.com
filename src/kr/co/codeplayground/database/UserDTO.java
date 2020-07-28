package kr.co.codeplayground.database;

import java.sql.Timestamp;

public class UserDTO {
	private String userId;
	private String userName;
	private String userPw;
	private String userGender;
	private Timestamp userBirth;
	private String userPhone;
	private Timestamp userRegdate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public Timestamp getUserBirth() {
		return userBirth;
	}

	public void setUserBirth(Timestamp userBirth) {
		this.userBirth = userBirth;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Timestamp getUserRegdate() {
		return userRegdate;
	}

	public void setUserRegdate(Timestamp userRegdate) {
		this.userRegdate = userRegdate;
	}

}
