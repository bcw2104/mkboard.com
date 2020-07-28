package com.codeplayground.service;

import java.util.ArrayList;

import com.codeplayground.database.CategoryDAO;
import com.codeplayground.database.CategoryDTO;

public class CategoryService {

	public ArrayList<CategoryDTO> getCategoryList() {
		CategoryDAO categoryDAO = new CategoryDAO();

		return categoryDAO.getAllCategoryData();
	}

	public CategoryDTO getCategoryInfo(String categoryId) {
		CategoryDAO categoryDAO = new CategoryDAO();

		return categoryDAO.getCategoryData(categoryId);
	}
}
