package com.codeplayground.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.codeplayground.entity.CommentDTO;
import com.codeplayground.entity.SubCommentDTO;
import com.codeplayground.util.DBUtil;

public class CommentDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public CommentDAO() {
		conn = DBUtil.getConnection();
		pstmt = null;
		rs = null;
	}

	public ArrayList<SubCommentDTO> getSubCommentList(int parentId) throws SQLException {
		ArrayList<SubCommentDTO> list = new ArrayList<SubCommentDTO>();
		String sql = "SELECT * FROM tbl_subcomment WHERE parent_id = ? ORDER BY create_date ASC";

		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, parentId);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			SubCommentDTO subCommentDTO = new SubCommentDTO();

			subCommentDTO.setCommentId(rs.getInt("comment_id"));
			subCommentDTO.setParentId(rs.getInt("parent_id"));
			subCommentDTO.setCommentContent(rs.getString("comment_content"));
			subCommentDTO.setUserId(rs.getString("user_id"));
			subCommentDTO.setCreateDate(rs.getTimestamp("create_date"));

			list.add(subCommentDTO);
		}
		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}
		return list;
	}

	public ArrayList<CommentDTO> getCommentList(int postId, String sort) throws SQLException {
		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		String sql = "SELECT * FROM tbl_comment WHERE post_id = ? ORDER BY create_date " + sort;

		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, postId);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			CommentDTO commentDTO = new CommentDTO();

			commentDTO.setCommentId(rs.getInt("comment_id"));
			commentDTO.setPostId(rs.getInt("post_id"));
			commentDTO.setCommentContent(rs.getString("comment_content"));
			commentDTO.setUserId(rs.getString("user_id"));
			commentDTO.setChildCount(rs.getInt("child_count"));
			commentDTO.setCreateDate(rs.getTimestamp("create_date"));

			list.add(commentDTO);
		}
		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}
		return list;
	}

	public void postComment(int postId, String commentContent, String userId) throws SQLException {

		String sql = "INSERT INTO tbl_comment VALUES((SELECT count(*) FROM tbl_comment)+1,?,?,?,sysdate,0)";

		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, postId);
		pstmt.setString(2, commentContent);
		pstmt.setString(3, userId);

		pstmt.executeUpdate();

		if (!pstmt.isClosed()) {
			pstmt.close();
		}
	}

	public void postSubComment(int parentId, String commentContent, String userId) throws SQLException {

		String sql = "INSERT INTO tbl_subcomment VALUES((SELECT count(*) FROM tbl_subcomment)+1,?,?,sysdate,?)";

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, commentContent);
		pstmt.setString(2, userId);
		pstmt.setInt(3, parentId);

		pstmt.executeUpdate();

		if (!pstmt.isClosed()) {
			pstmt.close();
		}
	}

	public void putCommentChild(int commentId) throws SQLException {

		String sql = "UPDATE tbl_comment SET child_count = child_count+1 where comment_id = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, commentId);
		pstmt.executeUpdate();

		if (!pstmt.isClosed()) {
			pstmt.close();
		}
	}
}
