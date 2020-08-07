package com.codeplayground.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.codeplayground.entity.BoardDTO;
import com.codeplayground.entity.CategoryDTO;

public interface CategoryMapperInterface {

	@Results({
		@Result(column = "category_id",property = "categoryId"),
		@Result(column = "category_name",property = "categoryName")
	})

	@Select("SELECT * FROM tbl_category")
	ArrayList<CategoryDTO> getCategoryAll();

	@Select("SELECT category_name FROM tbl_category  WHERE category_id = #{categoryId}")
	ArrayList<CategoryDTO> getCategoryListbyId(String categoryId);
}
