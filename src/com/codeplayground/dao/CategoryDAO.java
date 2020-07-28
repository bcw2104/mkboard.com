package com.codeplayground.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.codeplayground.entity.CategoryDTO;
import com.codeplayground.util.DBUtil;

public class CategoryDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public CategoryDAO() {
		conn = DBUtil.getConnection();
		pstmt = null;
		rs = null;
	}

	public ArrayList<CategoryDTO> getAllCategoryData() {
		String sql = "SELECT * FROM tbl_category";
		ArrayList<CategoryDTO> list = new ArrayList<CategoryDTO>();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CategoryDTO categoryDTO = new CategoryDTO();
				categoryDTO.setCategoryId(rs.getString("category_id"));
				categoryDTO.setCategoryName(rs.getString("category_name"));

				list.add(categoryDTO);
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

	public CategoryDTO getCategoryData(String categoryId) {
		String sql = "SELECT category_name FROM tbl_category  WHERE category_id = ?";
		CategoryDTO categoryDTO = new CategoryDTO();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, categoryId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				categoryDTO.setCategoryId(categoryId);
				categoryDTO.setCategoryName(rs.getString("category_name"));
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

		return categoryDTO;
	}

}
