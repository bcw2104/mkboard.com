package com.codeplayground.service;

import java.util.ArrayList;

import com.codeplayground.dao.CategoryDAO;
import com.codeplayground.entity.CategoryDTO;

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
