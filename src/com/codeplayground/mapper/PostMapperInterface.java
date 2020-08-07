package com.codeplayground.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.codeplayground.entity.BoardDTO;
import com.codeplayground.entity.CategoryDTO;

public interface PostMapperInterface {

	@Results({
		@Result(column = "post_id",property = "postId"),
		@Result(column = "post_title",property = "postTitle"),
		@Result(column = "post_content",property = "postContent"),
		@Result(column = "board_id",property = "boardId"),
		@Result(column = "create_date",property = "createDate")
	})

	@Select("SELECT * FROM tbl_category")
	ArrayList<CategoryDTO> getCategoryAll();

	@Select("SELECT category_name FROM tbl_category  WHERE category_id = #{categoryId}")
	ArrayList<CategoryDTO> getCategoryListbyId(String categoryId);
}
