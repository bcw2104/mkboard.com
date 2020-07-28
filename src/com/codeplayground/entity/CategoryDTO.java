package com.codeplayground.entity;

public class CategoryDTO {
	private String categoryId;
	private String categoryName;

	public CategoryDTO() {
		categoryId="";
		categoryName = "";
	}

	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
