package com.codeplayground.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

	// 회원 추가
	public boolean postUser(UserDTO userDTO) {
		String sql = "INSERT INTO tbl_user VALUES(?,?,?,?,?,?,sysdate)";
		boolean state = true;

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userDTO.getUserId());
			pstmt.setString(2, userDTO.getUserName());
			pstmt.setString(3, userDTO.getUserPw());
			pstmt.setString(4, userDTO.getUserGender());
			pstmt.setTimestamp(5, userDTO.getUserBirth());
			pstmt.setString(6, userDTO.getUserPhone());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("회원정보 삽입 중 오류가 발생했습니다.");
			e.printStackTrace();
			state = false;
		} finally {
			try {
				if(!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return state;
	}

	// 회원 검색
	public UserDTO getUser(String userId) {
		String sql = "SELECT * FROM tbl_user WHERE user_id=?";
		UserDTO userDTO = null;

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				userDTO = new UserDTO();

				userDTO.setUserId(rs.getString("user_id"));
				userDTO.setUserName(rs.getString("user_name"));
				userDTO.setUserPw(rs.getString("user_pw"));
				userDTO.setUserGender(rs.getString("user_gender"));
				userDTO.setUserBirth(rs.getTimestamp("user_birth"));
				userDTO.setUserPhone(rs.getString("user_phone"));
				userDTO.setUserRegdate(rs.getTimestamp("user_regdate"));
			}
		} catch (SQLException e) {
			System.out.println("회원 검색 중 오류가 발생했습니다.");
			e.printStackTrace();
		}finally {
			try {
				if(!rs.isClosed()) {
					rs.close();
				}
				if(!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return userDTO;
	}

	public int getTotalUserCount() {
		String sql = "SELECT count(*) as count FROM tbl_user";
		int totalCount = 0;
		try {
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				totalCount = rs.getInt("count");
			}
		} catch (SQLException e) {
			System.out.println("검색 중 오류가 발생했습니다.");
			e.printStackTrace();
		}finally {
			try {
				if(!rs.isClosed()) {
					rs.close();
				}
				if(!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return totalCount;
	}
	// 페이지별 회원 검색
	public ArrayList<UserDTO> getUserList(int pageNum) {
		String sql = "SELECT * FROM (SELECT rownum as row_num ,tbl_user.* FROM "
								+ "(SELECT * FROM tbl_user ORDER BY user_regdate ASC) tbl_user )"
								+ "WHERE row_num BETWEEN ? AND ?";
		ArrayList<UserDTO> userList = new ArrayList<UserDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 8*(pageNum-1)+1);
			pstmt.setInt(2, 8*pageNum);
			rs = pstmt.executeQuery();

			while(rs.next()) {
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
		} catch (SQLException e) {
			System.out.println("회원 검색 중 오류가 발생했습니다.");
			e.printStackTrace();
		}finally {
			try {
				if(!rs.isClosed()) {
					rs.close();
				}
				if(!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return userList;
	}

	//회원 수정
	public boolean putUser(UserDTO userDTO) {
		String sql = "UPDATE tbl_user SET user_name = ?, user_pw = ?, user_gender = ?, user_birth = ?, user_phone = ? "
							+ "WHERE user_id = ?";
		boolean state = true;

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userDTO.getUserName());
			pstmt.setString(2, userDTO.getUserPw());
			pstmt.setString(3, userDTO.getUserGender());
			pstmt.setTimestamp(4, userDTO.getUserBirth());
			pstmt.setString(5, userDTO.getUserPhone());
			pstmt.setString(6, userDTO.getUserId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("회원정보 갱신 중 오류가 발생했습니다.");
			e.printStackTrace();
			state = false;
		}finally {
			try {
				if(!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return state;
	}

	//회원 삭제
	public boolean deleteUser(UserDTO userDTO) {
		String sql = "DELETE FROM tbl_user WHERE user_id = ?";
		boolean state = true;
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userDTO.getUserId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("회원정보 삭제 중 오류가 발생했습니다.");
			e.printStackTrace();
			state = false;
		}finally {
			try {
				if(!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return state;
	}

}
