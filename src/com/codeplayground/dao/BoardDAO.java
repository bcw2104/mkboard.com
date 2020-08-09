
package com.codeplayground.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.codeplayground.entity.BoardDTO;
import com.codeplayground.util.DBUtil;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public BoardDAO() {
		conn = DBUtil.getConnection();
		pstmt = null;
		rs = null;
	}

	public BoardDTO getBoard(String boardId) throws SQLException {
		String sql = "SELECT board_name,category_id FROM tbl_board  WHERE board_id = ?";
		BoardDTO boardDTO = null;

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, boardId);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			boardDTO = new BoardDTO();
			boardDTO.setBoardId(boardId);
			boardDTO.setBoardName(rs.getString("board_name"));
			boardDTO.setCategoryId(rs.getString("category_id"));
		}

		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}

		return boardDTO;

	}

	public ArrayList<BoardDTO> getBoardList(String categoryId) throws SQLException{
		String sql = "SELECT board_id,board_name FROM tbl_board  WHERE category_id = ?";
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, categoryId);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			BoardDTO boardDTO = new BoardDTO();
			boardDTO.setBoardId(rs.getString("board_id"));
			boardDTO.setBoardName(rs.getString("board_name"));
			boardDTO.setCategoryId(categoryId);

			list.add(boardDTO);
		}

		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}

		return list;
	}

}
