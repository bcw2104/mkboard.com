package com.codeplayground.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.codeplayground.dao.CategoryDAO;
import com.codeplayground.entity.CategoryDTO;

public class CategoryService {
	private CategoryDAO categoryDAO;

	public CategoryService() {
		categoryDAO = new CategoryDAO();
	}

	public CategoryDTO getCategoryInfo(String categoryId) throws SQLException{
		return categoryDAO.getCategoryData(categoryId);
	}

	public ArrayList<CategoryDTO> getCategoryList() throws SQLException{
		return categoryDAO.getAllCategoryData();
	}
}
