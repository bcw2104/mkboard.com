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

	public BoardDTO getBoard(String boardId) {
		String sql = "SELECT board_name,category_id FROM tbl_board  WHERE board_id = ?";
		BoardDTO boardDTO = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				boardDTO = new BoardDTO();
				boardDTO.setBoardId(boardId);
				boardDTO.setBoardName(rs.getString("board_name"));
				boardDTO.setCategoryId(rs.getString("category_id"));
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

		return boardDTO;
	}

	public ArrayList<BoardDTO> getBoardList(String categoryId) {
		String sql = "SELECT board_id,board_name FROM tbl_board  WHERE category_id = ?";
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

		try {
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

}
