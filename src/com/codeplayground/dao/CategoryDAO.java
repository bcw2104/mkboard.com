
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

	public ArrayList<CategoryDTO> getAllCategoryData() throws SQLException {
		String sql = "SELECT * FROM tbl_category";
		ArrayList<CategoryDTO> list = new ArrayList<CategoryDTO>();

		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setCategoryId(rs.getString("category_id"));
			categoryDTO.setCategoryName(rs.getString("category_name"));

			list.add(categoryDTO);
		}

		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}

		return list;
	}

	public CategoryDTO getCategoryData(String categoryId) throws SQLException {
		String sql = "SELECT category_name FROM tbl_category  WHERE category_id = ?";
		CategoryDTO categoryDTO = null;

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, categoryId);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			categoryDTO = new CategoryDTO();
			categoryDTO.setCategoryId(categoryId);
			categoryDTO.setCategoryName(rs.getString("category_name"));
		}

		if (!rs.isClosed()) {
			rs.close();
		}
		if (!pstmt.isClosed()) {
			pstmt.close();
		}

		return categoryDTO;
	}

}
