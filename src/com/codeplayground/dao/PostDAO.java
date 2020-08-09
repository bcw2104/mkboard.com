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

	public int getPostCount(String categoryId, String postTitle, String author) throws SQLException {
		int count = 0;
		String sql = "SELECT count(*) as count FROM tbl_post NATURAL JOIN tbl_board "
				+ "WHERE category_id = ? AND post_title LIKE ? AND author LIKE ? ";

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, categoryId);
		pstmt.setString(2, "%" + postTitle + "%");
		pstmt.setString(3, "%" + author + "%");

		rs = pstmt.executeQuery();

		while (rs.next()) {
			count = rs.getInt("count");
		}

		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}

		return count;
	}

	public int getPostCount(String boardId, String categoryId, String postTitle, String author) throws SQLException {
		int count = 0;
		String sql = "SELECT count(*) as count FROM tbl_post NATURAL JOIN tbl_board "
				+ "WHERE board_id = ? AND category_id = ? AND post_title LIKE ? AND author LIKE ? ";

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, boardId);
		pstmt.setString(2, categoryId);
		pstmt.setString(3, "%" + postTitle + "%");
		pstmt.setString(4, "%" + author + "%");

		rs = pstmt.executeQuery();

		while (rs.next()) {
			count = rs.getInt("count");
		}

		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}
		return count;
	}

	public PostDTO getPost(int postId) throws SQLException {
		String sql = "SELECT * FROM tbl_post WHERE post_id = ?";
		PostDTO postDTO = null;

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

		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}

		return postDTO;
	}

	public ArrayList<PostDTO> getClosestPostList(int postId, String boardId) throws SQLException {
		ArrayList<PostDTO> list = new ArrayList<PostDTO>();

		String sql = "SELECT post_id,post_title,author,create_date,comments FROM (SELECT ROWNUM AS row_num, tbl_post.* FROM"
				+ " (SELECT * FROM tbl_post WHERE board_id=? ORDER BY post_id DESC) tbl_post) "
				+ "WHERE row_num = (SELECT row_num FROM  (SELECT ROWNUM AS row_num, tbl_post.* FROM "
				+ "(SELECT * FROM tbl_post WHERE board_id=? ORDER BY post_id DESC) tbl_post) where post_id = ?)+1 "
				+ "OR row_num = (SELECT row_num FROM  (SELECT ROWNUM AS row_num, tbl_post.* FROM "
				+ "(SELECT * FROM tbl_post WHERE board_id=? ORDER BY post_id DESC) tbl_post) where post_id = ?)-1 ";

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

		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}

		return list;
	}

	public ArrayList<PostDTO> getPostList(String categoryId, String field, String postTitle, String author, int pageNum)
			throws SQLException {
		ArrayList<PostDTO> list = new ArrayList<PostDTO>();

		String sql = "SELECT * FROM "
				+ "(SELECT rownum as row_num ,tbl_post.* FROM (SELECT * FROM tbl_post NATURAL JOIN "
				+ "(SELECT * FROM tbl_board WHERE category_id = ?) "
				+ "WHERE post_title LIKE ? AND author LIKE ? ORDER BY " + field + ") tbl_post)"
				+ "WHERE row_num BETWEEN ? AND ?";

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
		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}

		return list;
	}

	public ArrayList<PostDTO> getPostList(String boardId, String categoryId, String field, String postTitle,
			String author, int pageNum) throws SQLException {
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
		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}

		return list;
	}

	public void postPost(String postTitle, String postContent, String boardId, String author) throws SQLException {
		String sql = "INSERT INTO tbl_post VALUES((SELECT MAX(post_id)+1 FROM tbl_post),?,?,?,?,sysdate,0,0)";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, postTitle);
		pstmt.setString(2, postContent);
		pstmt.setString(3, boardId);
		pstmt.setString(4, author);

		pstmt.executeUpdate();

		if (!pstmt.isClosed()) {
			pstmt.close();
		}
	}

	public void putHits(int postId) throws SQLException {
		String sql = "UPDATE tbl_post SET hits=hits+1 WHERE post_id = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, postId);

		pstmt.executeUpdate();

		if (!pstmt.isClosed()) {
			pstmt.close();
		}
	}

	public void putComments(int postId) throws SQLException {
		String sql = "UPDATE tbl_post SET comments=comments+1 WHERE post_id = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, postId);

		pstmt.executeUpdate();
		if (!pstmt.isClosed()) {
			pstmt.close();
		}
	}

}
