package com.codeplayground.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.codeplayground.entity.BoardDTO;

public interface BoardMapperInterface {

	@Results({
		@Result(column = "board_id",property = "boardId"),
		@Result(column = "board_name",property = "boardName"),
		@Result(column = "category_id",property = "categoryId")
	})

	@Select("SELECT * FROM tbl_board  WHERE board_id = #{boardId}")
	ArrayList<BoardDTO> getBoardListbyId(String boardId);

	@Select("SELECT * FROM tbl_board  WHERE category_id = #{categoryId}")
	ArrayList<BoardDTO> getBoardListbyCategory(String categoryId);
}
