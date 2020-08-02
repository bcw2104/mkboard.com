package com.codeplayground.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.codeplayground.entity.PostDTO;
import com.codeplayground.util.DBUtil;

public class PostDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int pageUnit;

	public PostDAO() {
		conn = DBUtil.getConnection();
		pstmt = null;
		rs = null;

		pageUnit = 18;
	}

	public int getPostCount(String categoryId, String postTitle, String author) {
		int count = 0;
		String sql = "SELECT count(*) as count FROM tbl_post NATURAL JOIN tbl_board "
				+ "WHERE category_id = ? AND post_title LIKE ? AND author LIKE ? ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, categoryId);
			pstmt.setString(2, "%" + postTitle + "%");
			pstmt.setString(3, "%" + author + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt("count");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}

	public int getPostCount(String boardId, String categoryId, String postTitle, String author) {
		int count = 0;
		String sql = "SELECT count(*) as count FROM tbl_post NATURAL JOIN tbl_board "
				+ "WHERE board_id = ? AND category_id = ? AND post_title LIKE ? AND author LIKE ? ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardId);
			pstmt.setString(2, categoryId);
			pstmt.setString(3, "%" + postTitle + "%");
			pstmt.setString(4, "%" + author + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt("count");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}

	public PostDTO getPost(int postId) {
		String sql = "SELECT * FROM tbl_post WHERE post_id = ?";
		PostDTO postDTO = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				postDTO = new PostDTO();

				postDTO.setPostId(rs.getInt("post_id"));
				postDTO.setPostTitle(rs.getString("post_title"));
				postDTO.setPostContent(rs.getString("post_content"));
				postDTO.setBoardId(rs.getString("board_id"));
				postDTO.setAuthor(rs.getString("author"));
				postDTO.setCreateDate(rs.getTimestamp("create_date"));
				postDTO.setHits(rs.getInt("hits"));
				postDTO.setComments(rs.getInt("comments"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return postDTO;
	}

	public ArrayList<PostDTO> getClosestPostList(int postId, String boardId) {
		ArrayList<PostDTO> list = new ArrayList<PostDTO>();

		String sql = "SELECT post_id,post_title,author,create_date,comments FROM (SELECT ROWNUM AS row_num, tbl_post.* FROM"
				+" (SELECT * FROM tbl_post WHERE board_id=?) tbl_post) "
				+ "WHERE row_num = (SELECT row_num FROM  (SELECT ROWNUM AS row_num, tbl_post.* FROM "
				+ "(SELECT * FROM tbl_post WHERE board_id=?) tbl_post) where post_id = ?)+1 "
				+ "OR row_num = (SELECT row_num FROM  (SELECT ROWNUM AS row_num, tbl_post.* FROM "
				+ "(SELECT * FROM tbl_post WHERE board_id=?) tbl_post) where post_id = ?)-1 ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardId);
			pstmt.setString(2, boardId);
			pstmt.setInt(3, postId);
			pstmt.setString(4, boardId);
			pstmt.setInt(5, postId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostDTO postDTO = new PostDTO();
				postDTO.setPostId(rs.getInt("post_id"));
				postDTO.setPostTitle(rs.getString("post_title"));
				postDTO.setAuthor(rs.getString("author"));
				postDTO.setCreateDate(rs.getTimestamp("create_date"));
				postDTO.setComments(rs.getInt("comments"));

				list.add(postDTO);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public ArrayList<PostDTO> getPostList(String categoryId, String field, String postTitle, String author, int pageNum) {
		ArrayList<PostDTO> list = new ArrayList<PostDTO>();

		String sql = "SELECT * FROM "
				+ "(SELECT rownum as row_num ,tbl_post.* FROM (SELECT * FROM tbl_post NATURAL JOIN "
				+ "(SELECT * FROM tbl_board WHERE category_id = ?) "
				+ "WHERE post_title LIKE ? AND author LIKE ? ORDER BY " + field + ") tbl_post)"
				+ "WHERE row_num BETWEEN ? AND ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, categoryId);
			pstmt.setString(2, "%" + postTitle + "%");
			pstmt.setString(3, "%" + author + "%");
			pstmt.setInt(4, pageUnit * (pageNum - 1) + 1);
			pstmt.setInt(5, pageUnit * pageNum);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostDTO postDTO = new PostDTO();
				postDTO.setPostId(rs.getInt("post_id"));
				postDTO.setPostTitle(rs.getString("post_title"));
				postDTO.setBoardId(rs.getString("board_id"));
				postDTO.setAuthor(rs.getString("author"));
				postDTO.setCreateDate(rs.getTimestamp("create_date"));
				postDTO.setHits(rs.getInt("hits"));
				postDTO.setComments(rs.getInt("comments"));

				list.add(postDTO);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public ArrayList<PostDTO> getPostList(String boardId, String categoryId, String field, String postTitle,
			String author, int pageNum) {
		ArrayList<PostDTO> list = new ArrayList<PostDTO>();

		String condition = "=";
		if (boardId.equals("")) {
			condition = "LIKE";
			boardId = "%";
		}
		String sql = "SELECT * FROM "
				+ "(SELECT rownum as row_num ,tbl_post.* FROM (SELECT * FROM tbl_post NATURAL JOIN "
				+ "(SELECT * FROM tbl_board WHERE  board_id " + condition + " ? AND category_id = ?) "
				+ "WHERE post_title LIKE ? AND author LIKE ? ORDER BY " + field + ") tbl_post)"
				+ "WHERE row_num BETWEEN ? AND ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardId);
			pstmt.setString(2, categoryId);
			pstmt.setString(3, "%" + postTitle + "%");
			pstmt.setString(4, "%" + author + "%");
			pstmt.setInt(5, pageUnit * (pageNum - 1) + 1);
			pstmt.setInt(6, pageUnit * pageNum);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostDTO postDTO = new PostDTO();
				postDTO.setPostId(rs.getInt("post_id"));
				postDTO.setPostTitle(rs.getString("post_title"));
				postDTO.setBoardId(rs.getString("board_id"));
				postDTO.setAuthor(rs.getString("author"));
				postDTO.setCreateDate(rs.getTimestamp("create_date"));
				postDTO.setHits(rs.getInt("hits"));
				postDTO.setComments(rs.getInt("comments"));

				list.add(postDTO);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public boolean postPost(String postTitle,String postContent,String boardId,String author) {
		String sql = "INSERT INTO tbl_post VALUES((SELECT MAX(post_id)+1 FROM tbl_post),?,?,?,?,sysdate,0,0)";
		boolean state = true;

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, postTitle);
			pstmt.setString(2, postContent);
			pstmt.setString(3, boardId);
			pstmt.setString(4, author);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			state = false;
		} finally {
			try {
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return state;
	}

	public boolean putHits(int postId) {
		String sql = "UPDATE tbl_post SET hits=hits+1 WHERE post_id = ?";
		boolean state = true;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			state = false;
		} finally {
			try {
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return state;
	}

	public boolean putComments(int postId) {
		String sql = "UPDATE tbl_post SET comments=comments+1 WHERE post_id = ?";
		boolean state = true;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("댓글수 증가 중 오류가 발생했습니다.");
			state = false;
		} finally {
			try {
				if (!pstmt.isClosed()) {
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
