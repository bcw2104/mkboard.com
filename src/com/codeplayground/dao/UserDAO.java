package com.codeplayground.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.codeplayground.entity.UserDTO;
import com.codeplayground.util.DBUtil;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public UserDAO() {
		conn = DBUtil.getConnection();
		pstmt = null;
		rs = null;
	}

	public void postUser(UserDTO userDTO) throws SQLException {
		String sql = "INSERT INTO tbl_user VALUES(?,?,?,?,?,?,sysdate)";
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, userDTO.getUserId());
		pstmt.setString(2, userDTO.getUserName());
		pstmt.setString(3, userDTO.getUserPw());
		pstmt.setString(4, userDTO.getUserGender());
		pstmt.setTimestamp(5, userDTO.getUserBirth());
		pstmt.setString(6, userDTO.getUserPhone());

		pstmt.executeUpdate();

		if (!pstmt.isClosed()) {
			pstmt.close();
		}
	}

	// 회원 검색
	public UserDTO getUser(String userId) throws SQLException {
		String sql = "SELECT * FROM tbl_user WHERE user_id=?";
		UserDTO userDTO = null;

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, userId);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			userDTO = new UserDTO();

			userDTO.setUserId(rs.getString("user_id"));
			userDTO.setUserName(rs.getString("user_name"));
			userDTO.setUserPw(rs.getString("user_pw"));
			userDTO.setUserGender(rs.getString("user_gender"));
			userDTO.setUserBirth(rs.getTimestamp("user_birth"));
			userDTO.setUserPhone(rs.getString("user_phone"));
			userDTO.setUserRegdate(rs.getTimestamp("user_regdate"));
		}

		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}

		return userDTO;
	}

	public int getTotalUserCount() throws SQLException {
		String sql = "SELECT count(*) as count FROM tbl_user";
		int totalCount = 0;
		pstmt = conn.prepareStatement(sql);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			totalCount = rs.getInt("count");
		}

		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}
		return totalCount;
	}

	// 페이지별 회원 검색
	public ArrayList<UserDTO> getUserList(int pageNum) throws SQLException {
		String sql = "SELECT * FROM (SELECT rownum as row_num ,tbl_user.* FROM "
				+ "(SELECT * FROM tbl_user ORDER BY user_regdate ASC) tbl_user )" + "WHERE row_num BETWEEN ? AND ?";
		ArrayList<UserDTO> userList = new ArrayList<UserDTO>();

		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, 8 * (pageNum - 1) + 1);
		pstmt.setInt(2, 8 * pageNum);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			UserDTO userDTO = new UserDTO();

			userDTO.setUserId(rs.getString("user_id"));
			userDTO.setUserName(rs.getString("user_name"));
			userDTO.setUserPw(rs.getString("user_pw"));
			userDTO.setUserGender(rs.getString("user_gender"));
			userDTO.setUserBirth(rs.getTimestamp("user_birth"));
			userDTO.setUserPhone(rs.getString("user_phone"));
			userDTO.setUserRegdate(rs.getTimestamp("user_regdate"));

			userList.add(userDTO);
		}
		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}

		return userList;
	}

	// 회원 수정
	public void putUser(UserDTO userDTO) throws SQLException {
		String sql = "UPDATE tbl_user SET user_name = ?, user_pw = ?, user_gender = ?, user_birth = ?, user_phone = ? "
				+ "WHERE user_id = ?";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, userDTO.getUserName());
		pstmt.setString(2, userDTO.getUserPw());
		pstmt.setString(3, userDTO.getUserGender());
		pstmt.setTimestamp(4, userDTO.getUserBirth());
		pstmt.setString(5, userDTO.getUserPhone());
		pstmt.setString(6, userDTO.getUserId());

		pstmt.executeUpdate();

		if (!pstmt.isClosed()) {
			pstmt.close();
		}
	}

	// 회원 삭제
	public void deleteUser(UserDTO userDTO) throws SQLException {
		String sql = "DELETE FROM tbl_user WHERE user_id = ?";
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, userDTO.getUserId());

		pstmt.executeUpdate();

		if (!pstmt.isClosed()) {
			pstmt.close();
		}
	}

}
